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

    @GetMapping("/watchedStory/{id}")
    public ResponseEntity<?> getWatchedUsers(@PathVariable String id){
        try{
            return ResponseEntity.ok(storyService.getWatchedUsers(id));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get watched users: " + e.getMessage());
        }
    }

    @PostMapping("/addWatchedUser/{storyId}/{userId}")
    public ResponseEntity<?> addWatchedUsers(@PathVariable String storyId, @PathVariable String userId){
        try{
            return ResponseEntity.ok(storyService.addWatchedUser(storyId, userId));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add watched users: " + e.getMessage());
        }
    }
    

    @DeleteMapping("/deleteStory/{id}")
    public ResponseEntity<?> deleteStory(@PathVariable String id){
        try{
            storyService.deleteStory(id);
            return ResponseEntity.ok("Story deleted successfully");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete story: " + e.getMessage());
        }
    }
}
