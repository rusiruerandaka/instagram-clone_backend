package com.example.backend.controller;

import com.example.backend.model.Post;
import com.example.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;


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

    @PostMapping("/incrementLikes/{postId}")
    public ResponseEntity<?> incrementLikes(@PathVariable String postId){
        try{
            return ResponseEntity.ok(postService.incrementLikes(postId));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to increase likes: " + e.getMessage());
        }
    }

    @PostMapping("/decrementLikes/{postId}")
    public ResponseEntity<?> decrementLikes(@PathVariable String postId){
        try{
            return ResponseEntity.ok(postService.decrementLikes(postId));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to decrease likes: " + e.getMessage());
        }
    }

    @GetMapping("/getLikeCount/{postId}")
    public Integer getLikeCount(@PathVariable String postId) {
        return postService.getLikeCount(postId);
    }

    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<?> deletePost(@PathVariable String id){
        try{
            postService.deletePost(id);
            return ResponseEntity.ok("Post deleted successfully");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete post: " + e.getMessage());
        }
    }

    @PutMapping("/editPost/{id}")
    public ResponseEntity<?> editPost(@PathVariable String id, @RequestBody Post post){
        try{
            return ResponseEntity.ok(postService.editPost(id, post));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to edit post: " + e.getMessage());
        }
    }
}
