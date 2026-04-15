package ru.job4j.github.analysis.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubCommitDto {
    private String sha;

    @JsonIgnore
    private String message;

    @JsonIgnore
    private String author;

    @JsonIgnore
    private LocalDateTime date;

    @JsonProperty("commit")
    private void unpackCommit(Map<String, Object> commit) {
        this.message = (String) commit.get("message");
        Map<String, String> author = (Map<String, String>) commit.get("author");
        this.author = author.get("name");
        this.date = LocalDateTime.parse(author.get("date"),
            DateTimeFormatter.ISO_DATE_TIME);
    }
}