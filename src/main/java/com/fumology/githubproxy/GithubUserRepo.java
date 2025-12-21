package com.fumology.githubproxy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GithubUserRepos {

    @JsonProperty("incomplete_results")
    private boolean incompleteResults;

    private List<Repo> items;

    @Data
    public static class Repo {
        private long id;

        private String name;

        @JsonProperty("fork")
        private boolean isFork;

        @JsonProperty("full_name")
        private String fullName;

        private Owner owner;
    }

    @Data
    public static class Owner {
        private String login;
    }
}
