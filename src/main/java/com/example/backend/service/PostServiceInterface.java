package com.example.backend.service;

import java.util.List;

import com.example.backend.model.Post;

public interface PostServiceInterface {
    public Post getPostById(String id);
    public List<Post> getAllPosts();
    public Post addPost(Post post);
    public Post updatePost(Post post, String id);
    public void deletePost(String id);
    public String generateSequence(String seqName);
}