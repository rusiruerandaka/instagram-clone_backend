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
        User user = userRepository.findById(userId).get();
        User followUser = userRepository.findById(followUserId).get();

        Set<String> following = new HashSet<>(user.getFollowing());
        following.add(followUserId);
        user.setFollowing(Arrays.asList(following.toArray(new String[0])));

        Set<String> followers = new HashSet<>(followUser.getFollowers());
        followers.add(userId);
        followUser.setFollowers(Arrays.asList(followers.toArray(new String[0])));

        userRepository.save(user);
        userRepository.save(followUser);

        return user;
    }

    @Override
    public User unfollowUser(String userId, String unfollowUserId) {
        User user = userRepository.findById(userId).get();
        User unfollowUser = userRepository.findById(unfollowUserId).get();

        Set<String> following = new HashSet<>(user.getFollowing());
        following.remove(unfollowUserId);
        user.setFollowing(Arrays.asList(following.toArray(new String[0])));

        Set<String> followers = new HashSet<>(unfollowUser.getFollowers());
        followers.remove(userId);
        unfollowUser.setFollowers(Arrays.asList(followers.toArray(new String[0])));

        userRepository.save(user);
        userRepository.save(unfollowUser);

        return user;
    }
}