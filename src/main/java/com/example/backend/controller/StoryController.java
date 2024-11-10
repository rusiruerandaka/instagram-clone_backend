package com.example.backend.controller;

import com.example.backend.model.Story;
import com.example.backend.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class StoryController {

    @Autowired
    private StoryService storyService;

    @GetMapping("/getAllStories")
    public List<Story> getAllStories(){
        return storyService.getAllStories();
    }

    @GetMapping("/getStoryById/{id}")
    public Story getStoryById(@PathVariable String id){
        return storyService.getStoryById(id);
    }

    @PostMapping("/addStory")
    public ResponseEntity<Story> addStory(@RequestBody Story story){
        return new ResponseEntity<Story>(storyService.addStory(story), HttpStatus.CREATED);
    }
}
