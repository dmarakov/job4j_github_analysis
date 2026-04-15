package ru.job4j.github.analysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GitHubAnalyticsSpringBootMain {
    public static void main(String[] args) {
        SpringApplication.run(GitHubAnalyticsSpringBootMain.class, args);
    }
}
