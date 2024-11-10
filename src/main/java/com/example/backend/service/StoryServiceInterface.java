package com.example.backend.service;

import java.util.List;

import com.example.backend.model.Story;

public interface StoryServiceInterface {
    public List<Story> getAllStories();
    public Story getStoryById(String id);
    public Story addStory(Story story);
    public String generateSequence(String seqName);
}
