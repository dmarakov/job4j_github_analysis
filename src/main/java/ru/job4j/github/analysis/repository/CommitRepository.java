package ru.job4j.github.analysis.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;

import java.util.List;
import java.util.Optional;

public interface CommitRepository extends ListCrudRepository<Commit, Long> {

    Optional<Commit> findTopByRepositoryOrderByDateDesc(Repository repository);

    List<Commit> findCommitsByRepository(Repository repository);
}
