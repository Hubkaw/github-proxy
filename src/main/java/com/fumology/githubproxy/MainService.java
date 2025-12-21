package com.fumology.githubproxy;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MainService {

    private GithubFetcher githubFetcher;

    public Output getRepositoriesByLogin(String login) {

        List<GithubUserRepo> fetched = githubFetcher.getUserRepositories(login);

        if (fetched == null) {
            return null;
        }

        return mapRepos(fetched);
    }

    private Output mapRepos(List<GithubUserRepo> userRepos) {
        Output output = new Output();

        userRepos.stream()
                .filter(r -> !r.isFork())
                .forEach(i -> {
                    Output.Repository repository = new Output.Repository();
                    repository.setName(i.getName());
                    repository.setOwnerLogin(i.getOwner().getLogin());
                    fetchAndMapBranches(i.getOwner().getLogin(), i, repository);
                    output.getRepositories().add(repository);
                });

        return output;
    }

    private void fetchAndMapBranches(String login, GithubUserRepo githubRepo, Output.Repository outputRepository) {
        List<GithubBranch> githubBranches = githubFetcher.getRepoBranches(login, githubRepo.getName());
        if (githubBranches != null) {
            githubBranches.forEach(b -> {
                Output.Branch branch = new Output.Branch();
                branch.setName(b.getName());
                branch.setLastCommitSHA(b.getCommit().getSha());
                outputRepository.getBranches().add(branch);
            });
        }
    }

}
