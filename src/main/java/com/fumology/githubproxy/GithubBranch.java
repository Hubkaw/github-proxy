package com.fumology.githubproxy;

public record GithubBranch(
        String name,
        Commit commit
) {}
