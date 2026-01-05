package com.fumology.githubproxy;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@AllArgsConstructor
public class GithubFetcher {

    private final RestClient githubRestClient;

    private static final String REPO_URL = "/users/{login}/repos";
    private static final String BRANCH_URL = "/repos/{login}/{repo}/branches";

    public List<GithubUserRepo> getUserRepositories(String login) {
        return githubRestClient.get()
                .uri(REPO_URL, login)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) ->
                {
                    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new UserNotFoundException("There is no user with login %s".formatted(login));
                    }
                    throw new RuntimeException("GitHub error: " + response.getStatusCode());
                }))
                .body(new ParameterizedTypeReference<List<GithubUserRepo>>() {});
    }

    public List<GithubBranch> getRepoBranches(String login, String repo) {
        return githubRestClient.get()
                .uri(BRANCH_URL, login, repo)
                .retrieve()
                .body(new ParameterizedTypeReference<List<GithubBranch>>() {});
    }

}
