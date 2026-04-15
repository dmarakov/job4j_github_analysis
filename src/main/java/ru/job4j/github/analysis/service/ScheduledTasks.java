package ru.job4j.github.analysis.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;

import java.util.List;

@AllArgsConstructor
@Service
public class ScheduledTasks {
    private final RepositoryService repositoryService;
    private final CommitService commitService;
    private final GitHubService gitHubService;


    @Scheduled(fixedRateString = "${scheduler.fixedRate}")
    public void fetchCommits() {
        repositoryService.findAllRepository().stream()
            .flatMap(repo -> fetchCommitsForRepo(repo).stream())
            .forEach(commitService::save);
    }

    private List<Commit> fetchCommitsForRepo(Repository repo) {
        return commitService.findLastCommit(repo)
            .map(last -> gitHubService.fetchCommitsSince(repo.getOwnerLogin(), repo.getName(), last.getDate())
                .stream()
                .filter(commit -> !commit.getSha().equals(last.getSha()))
                .toList())
            .orElseGet(() -> gitHubService.getAllCommits(repo.getOwnerLogin(), repo.getName()).stream()
                .toList());
    }
}
