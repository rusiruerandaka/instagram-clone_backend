package com.example.backend.controller;

import com.example.backend.model.Post;
import com.example.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/getPostById/{id}")
    public Post findById(@PathVariable String id){
        return postService.getPostById(id);
    }

    @GetMapping("/getAllPosts")
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }

    @PostMapping("/addPost")
    public ResponseEntity<Post> addPost(@RequestBody Post post){
        return new ResponseEntity<Post>(postService.addPost(post), HttpStatus.CREATED);
    }
}
