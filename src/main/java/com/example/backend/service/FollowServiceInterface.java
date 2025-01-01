package com.example.backend.service;

import com.example.backend.model.User;

public interface FollowServiceInterface {
    User followUser(String userId, String followUserId);
}