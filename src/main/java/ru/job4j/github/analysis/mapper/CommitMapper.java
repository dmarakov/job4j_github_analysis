package ru.job4j.github.analysis.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.github.analysis.dto.GitHubCommitDto;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;

@Mapper(componentModel = "spring")
public interface CommitMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "repository", source = "repository")
    Commit toEntity(GitHubCommitDto dto, Repository repository);
}
