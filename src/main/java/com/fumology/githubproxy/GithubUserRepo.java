package com.fumology.githubproxy;


public record GithubUserRepo(
        String name,
        Owner owner,
        boolean fork
) {}

