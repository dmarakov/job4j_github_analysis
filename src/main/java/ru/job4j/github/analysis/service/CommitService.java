package ru.job4j.github.analysis.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;
import ru.job4j.github.analysis.repository.CommitRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommitService {

    private final CommitRepository commitRepository;

    public Optional<Commit> findLastCommit(Repository repository) {
        return commitRepository.findTopByRepositoryOrderByDateDesc(repository);
    }

    public void save(Commit commit) {
        commitRepository.save(commit);
    }

    public List<Commit> findByRepository(Repository repository) {
        return commitRepository.findCommitsByRepository(repository);
    }

    public void deleteAll() {
        commitRepository.deleteAll();
    }

}
