package com.fumology.githubproxy;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Output {

    private List<Repository> repositories = new ArrayList();


    @Data
    public static class Repository {
        private String name;
        private String ownerLogin;
        private List<Branch> branches = new ArrayList();

    }

    @Data
    public static class Branch {
        private String name;
        private String lastCommitSHA;
    }
}
