package ru.job4j.github.analysis.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.github.analysis.model.Repository;

import java.util.Optional;

public interface RepositoryRepository extends ListCrudRepository<Repository, Long> {

    Optional<Repository> findRepositoriesByName(String name);

}
