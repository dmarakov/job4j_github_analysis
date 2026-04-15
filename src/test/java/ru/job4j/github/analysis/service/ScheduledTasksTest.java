package ru.job4j.github.analysis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduledTasksTest {

    @Mock
    private RepositoryService repositoryService;

    @Mock
    private CommitService commitService;

    @Mock
    private GitHubService gitHubService;

    @InjectMocks
    private ScheduledTasks scheduledTasks;

    private Repository repo;

    @BeforeEach
    void setUp() {
        repo = new Repository();
        repo.setName("job4j_design");
        repo.setOwnerLogin("dmarakov");
    }

    @Test
    void whenNoCommitsInDbThenFetchAllFromGitHub() {
        Commit commit = new Commit();
        commit.setSha("abc123");
        when(repositoryService.findAllRepository()).thenReturn(List.of(repo));
        when(commitService.findLastCommit(repo)).thenReturn(Optional.empty());
        when(gitHubService.getAllCommits("dmarakov", "job4j_design"))
            .thenReturn(List.of(commit));
        scheduledTasks.fetchCommits();
        verify(gitHubService).getAllCommits("dmarakov", "job4j_design");
        verify(commitService).save(commit);
    }

    @Test
    void whenLastCommitExistsThenFetchOnlyNewCommits() {
        Commit lastCommit = new Commit();
        lastCommit.setSha("old123");
        lastCommit.setDate(LocalDateTime.of(2026, 1, 1, 0, 0));
        Commit newCommit = new Commit();
        newCommit.setSha("new456");
        when(repositoryService.findAllRepository()).thenReturn(List.of(repo));
        when(commitService.findLastCommit(repo)).thenReturn(Optional.of(lastCommit));
        when(gitHubService.fetchCommitsSince("dmarakov", "job4j_design", lastCommit.getDate()))
            .thenReturn(List.of(lastCommit, newCommit));
        scheduledTasks.fetchCommits();
        verify(commitService, never()).save(lastCommit);
        verify(commitService).save(newCommit);
    }

    @Test
    void whenAllCommitsAlreadyInDbThenNothingNewSaved() {
        Commit lastCommit = new Commit();
        lastCommit.setSha("old123");
        lastCommit.setDate(LocalDateTime.of(2026, 1, 1, 0, 0));
        when(repositoryService.findAllRepository()).thenReturn(List.of(repo));
        when(commitService.findLastCommit(repo)).thenReturn(Optional.of(lastCommit));
        when(gitHubService.fetchCommitsSince("dmarakov", "job4j_design", lastCommit.getDate()))
            .thenReturn(List.of(lastCommit));
        scheduledTasks.fetchCommits();
        verify(commitService, never()).save(any());
    }

    @Test
    void whenNoRepositoriesThenNoCommitsSaved() {
        when(repositoryService.findAllRepository()).thenReturn(List.of());
        scheduledTasks.fetchCommits();
        verify(commitService, never()).save(any());
    }
}