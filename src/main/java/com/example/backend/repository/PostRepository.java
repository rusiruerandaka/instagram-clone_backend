package com.example.backend.repository;

import com.example.backend.model.Post;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {

    List<Post> findPostIdByUserId(String userId);
}
