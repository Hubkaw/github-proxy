package com.fumology.githubproxy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GithubUserRepo {

    private String name;

    @JsonProperty("fork")
    private boolean isFork;

    @JsonProperty("full_name")
    private String fullName;

    private Owner owner;


    @Data
    public static class Owner {
        private String login;
    }
}
