package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.backend.model.Story;
import com.example.backend.repository.StoryRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class StoryCleanupService {

    @Autowired
    private StoryRepository storyRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Scheduled(cron = "0 0 * * * *") // Run every hour
    public void deleteExpiredStories() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24);

        // Find expired stories
        List<Story> expiredStories = storyRepository.findAll();
        expiredStories.stream()
                .filter(story -> LocalDateTime.parse(story.getDate(), DATE_FORMATTER).isBefore(cutoffTime))
                .forEach(storyRepository::delete);

        System.out.println("Expired stories deleted successfully.");
    }
}
