package ru.job4j.github.analysis.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.job4j.github.analysis.model.Repository;
import ru.job4j.github.analysis.repository.RepositoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RepositoryService {

    private final RepositoryRepository repositoryRepository;

    @Async
    public void create(Repository repository) {
        repositoryRepository.save(repository);
    }

    public List<Repository> findAllRepository() {
        return repositoryRepository.findAll();
    }

    public Optional<Repository> findRepoByName(String name) {
        return repositoryRepository.findRepositoriesByName(name);
    }

    public void deleteAll() {
        repositoryRepository.deleteAll();
    }
}
