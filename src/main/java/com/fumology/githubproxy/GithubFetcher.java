package com.fumology.githubproxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class GithubFetcher {

    @Value("${github.base-url}")
    private String baseUrl;

    private static final String REPO_URL = "%s/users/%s/repos";
    private static final String BRANCH_URL = "%s/repos/%s/%s/branches";

    public List<GithubUserRepo> getUserRepositories(String login) {
        String url = REPO_URL.formatted(baseUrl, login);

        try {

            ResponseEntity<GithubUserRepo[]> response = new RestTemplate().getForEntity(url, GithubUserRepo[].class);

            if (response.getBody() == null) {
                return null;
            }

            return List.of(response.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    public List<GithubBranch> getRepoBranches(String login, String repoName) {
        String url = BRANCH_URL.formatted(baseUrl, login, repoName);

        try {
            ResponseEntity<GithubBranch[]> response = new RestTemplate().getForEntity(url, GithubBranch[].class);

            if (response.getBody() == null) {
                return null;
            }

            return List.of(response.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

}
