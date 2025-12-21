package com.fumology.githubproxy;


import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MainControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort()).build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("github.base-url", wireMockServer::baseUrl);
    }

    @Test
    void shouldReturnRepositoriesWithBranches_whenUserExistsAndHasNonForkRepos() throws Exception {
        String login = "hubkaw";

        wireMockServer.stubFor(get(urlEqualTo("/users/hubkaw/repos"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("user-repos-with-non-fork.json")));

        wireMockServer.stubFor(get(urlEqualTo("/repos/hubkaw/Kuroneko-Botto/branches"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("branches-kuroneko.json")));

        mockMvc.perform(get("/{login}", login))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.repositories", hasSize(1)))
                .andExpect(jsonPath("$.repositories[0].name").value("Kuroneko-Botto"))
                .andExpect(jsonPath("$.repositories[0].ownerLogin").value("hubkaw"))
                .andExpect(jsonPath("$.repositories[0].branches", hasSize(2)))
                .andExpect(jsonPath("$.repositories[0].branches[0].name").value("master"))
                .andExpect(jsonPath("$.repositories[0].branches[0].lastCommitSHA").value("7a8f9e8d7c6b5a4f3e2d1c0b9a8f7e6d5c4b3a21"))
                .andExpect(jsonPath("$.repositories[0].branches[1].name").value("develop"));
    }

    @Test
    void shouldReturnEmptyList_whenUserHasOnlyForkedRepos() throws Exception {
        String login = "hubkaw-fork";

        wireMockServer.stubFor(get(urlEqualTo("/users/hubkaw-fork/repos"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("user-repos-only-forks.json")));


        mockMvc.perform(get("/{login}", login))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.repositories", empty()));
    }

    @Test
    void shouldReturn404WithCustomMessage_whenUserDoesNotExist() throws Exception {
        String login = "hubkaw2";

        wireMockServer.stubFor(get(urlEqualTo("/users/hubkaw2/repos"))
                .willReturn(aResponse().withStatus(404)));

        mockMvc.perform(get("/{login}", login))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("There is no user named hubkaw2."));
    }



}
