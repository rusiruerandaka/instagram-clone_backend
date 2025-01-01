package com.example.backend.service;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class FollowService implements FollowServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User followUser(String userId, String followUserId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        User followUser = userRepository.findById(followUserId).orElseThrow(() -> new RuntimeException("User to follow not found"));

        Set<String> following = new HashSet<>(Arrays.asList(user.getFollowing()));
        Set<String> followers = new HashSet<>(Arrays.asList(followUser.getFollowers()));

        following.add(followUserId);
        followers.add(userId);

        user.setFollowing(following.toArray(new String[0]));
        followUser.setFollowers(followers.toArray(new String[0]));

        userRepository.save(user);
        userRepository.save(followUser);

        return user;
    }
}