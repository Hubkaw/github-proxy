package com.fumology.githubproxy;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OutputDTO {

    private List<RepositoryDTO> repositories = new ArrayList();


    @Data
    public static class RepositoryDTO {
        private String name;
        private String ownerLogin;
        private List<BranchDTO> branches = new ArrayList();

    }

    @Data
    public static class BranchDTO {
        private String name;
        private String lastCommitSHA;
    }
}
