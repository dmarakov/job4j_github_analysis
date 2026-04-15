package ru.job4j.github.analysis.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.github.analysis.mapper.CommitMapper;
import ru.job4j.github.analysis.mapper.RepositoryMapper;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class GitHubService {

    private final GitHubRemote gitHubRemote;
    private final RepositoryService repositoryService;
    private final CommitMapper commitMapper;
    private final RepositoryMapper repositoryMapper;

    public List<Repository> getAllRepositories(String userName) {
        return gitHubRemote.fetchRepositories(userName).stream()
            .map(repositoryMapper::toEntity)
            .toList();
    }

    public List<Commit> getAllCommits(String userName, String repoName) {
        Repository repository = repositoryService.findRepoByName(repoName)
            .orElseThrow(() -> new NoSuchElementException(
                "Repository not found: " + repoName));
        return gitHubRemote.fetchCommits(userName, repoName).stream()
            .map(dto -> commitMapper.toEntity(dto, repository))
            .toList();
    }

    public List<Commit> fetchCommitsSince(String userName, String repoName, LocalDateTime since) {
        Repository repository = repositoryService.findRepoByName(repoName)
            .orElseThrow(() -> new NoSuchElementException(
                "Repository not found: " + repoName));
        return gitHubRemote.fetchCommitsSince(userName, repoName, since).stream()
            .map(dto -> commitMapper.toEntity(dto, repository))
            .toList();
    }
}