package ru.job4j.github.analysis.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;
import ru.job4j.github.analysis.service.CommitService;
import ru.job4j.github.analysis.service.GitHubRemote;
import ru.job4j.github.analysis.service.RepositoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class GitHubController {

    private RepositoryService repositoryService;
    private CommitService commitService;

    @GetMapping("/repositories")
    public List<Repository> getAllRepositories() {
        return repositoryService.findAllRepository();
    }

    @GetMapping("/commits/{name}")
    public List<Commit> getCommits(@PathVariable(value = "name") String name) {
        Optional<Repository> repository = repositoryService.findRepoByName(name);
        if (repository.isEmpty()) {
            throw new RuntimeException();
        }
        return commitService.findByRepository(repository.get());
    }

    @PostMapping("/repository")
    public ResponseEntity<Void> create(@RequestBody Repository repository) {
        repositoryService.create(repository);
        return ResponseEntity.noContent().build();
    }
}
