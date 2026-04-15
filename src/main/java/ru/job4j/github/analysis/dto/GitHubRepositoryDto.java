package ru.job4j.github.analysis.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepositoryDto {
    private Long id;
    private String name;
    @JsonProperty("html_url")
    private String url;
    private String ownerLogin;

    @JsonProperty("owner")
    private void unpackOwner(Map<String, Object> owner) {
        this.ownerLogin = (String) owner.get("login");
    }
}
