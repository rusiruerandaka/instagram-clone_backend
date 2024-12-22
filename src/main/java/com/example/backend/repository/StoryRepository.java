package com.example.backend.repository;

import com.example.backend.model.Story;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StoryRepository extends MongoRepository<Story, String> {
    @SuppressWarnings("null")
    Optional<Story> findById(String id);
}