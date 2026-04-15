package ru.job4j.github.analysis.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.github.analysis.dto.GitHubRepositoryDto;
import ru.job4j.github.analysis.model.Repository;

@Mapper(componentModel = "spring")
public interface RepositoryMapper {

    @Mapping(target = "id", ignore = true)
    Repository toEntity(GitHubRepositoryDto dto);
}
