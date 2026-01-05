package com.fumology.githubproxy;


import java.util.List;

public record Output(List<Repository> repositories) {

    public record Repository(String name, String ownerLogin, List<Branch> branches) {}

    public record Branch(String name, String lastCommitSHA) {}
}
