package com.fumology.githubproxy;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MainService {

    private GithubFetcher githubFetcher;

    public Output getRepositoriesByLogin(String login) {
        List<GithubUserRepo> allRepos = githubFetcher.getUserRepositories(login);

        List<Output.Repository> nonForkRepos = allRepos.stream()
                .filter(repo -> !repo.fork())
                .map(this::mapRepository)
                .toList();

        return new Output(nonForkRepos);
    }

    private Output.Repository mapRepository(GithubUserRepo repo) {
        List<Output.Branch> branches = githubFetcher.getRepoBranches(repo.owner().login(), repo.name())
                .stream()
                .map(b -> new Output.Branch(b.name(), b.commit().sha()))
                .toList();

        return new Output.Repository(repo.name(), repo.owner().login(), branches);
    }

}
