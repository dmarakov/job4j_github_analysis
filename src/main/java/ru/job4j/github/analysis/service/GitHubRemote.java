package ru.job4j.github.analysis.service;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.job4j.github.analysis.dto.GitHubCommitDto;
import ru.job4j.github.analysis.dto.GitHubRepositoryDto;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class GitHubRemote {

    private final RestTemplate restTemplate;


    public List<GitHubRepositoryDto> fetchRepositories(String username) {
        String url = "https://api.github.com/users/" + username + "/repos";
        ResponseEntity<List<GitHubRepositoryDto>> response = restTemplate.exchange(
            url, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<GitHubRepositoryDto>>() {
            });
        return response.getBody();
    }

    public List<GitHubCommitDto> fetchCommits(String userName, String repoName) {
        String url = String.format("https://api.github.com/repos/%s/%s/commits", userName, repoName);
        ResponseEntity<List<GitHubCommitDto>> response = restTemplate.exchange(
            url, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<GitHubCommitDto>>() {

            });
        return response.getBody();
    }

    public List<GitHubCommitDto> fetchCommitsSince(String owner, String repoName, LocalDateTime since) {
        String url = "https://api.github.com/repos/" + owner + "/" + repoName
            + "/commits?since=" + since.atZone(ZoneOffset.UTC)
            .format(DateTimeFormatter.ISO_INSTANT);
        ResponseEntity<List<GitHubCommitDto>> response = restTemplate.exchange(
            url, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<GitHubCommitDto>>() {

            });
        return response.getBody();
    }
}
