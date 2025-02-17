package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.model.User;
import com.example.backend.service.FollowService;


@CrossOrigin
@RestController
public class FollowController {
    @Autowired
    private FollowService followService;

    @PostMapping("/followUser/{userId}/{followUserId}")
    public ResponseEntity<?> followUser(@PathVariable String userId, @PathVariable String followUserId) {
        try {
            User user = followService.followUser(userId, followUserId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to follow user: " + e.getMessage());
        }
    }

    @DeleteMapping("/unfollowUser/{userId}/{unfollowUserId}")
    public ResponseEntity<?> unfollowUser(@PathVariable String userId, @PathVariable String unfollowUserId) {
        try {
            User user = followService.unfollowUser(userId, unfollowUserId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to unfollow user: " + e.getMessage());
        }
    }
}