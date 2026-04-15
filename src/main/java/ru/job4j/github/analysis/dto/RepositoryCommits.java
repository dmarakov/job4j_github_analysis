package ru.job4j.github.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RepositoryCommits {
    private String repositoryName;
    private String message;
    private String author;
    private LocalDateTime date;
}
