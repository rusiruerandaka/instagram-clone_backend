package com.example.backend.controller;

import com.example.backend.model.Comment;
import com.example.backend.model.User;
import com.example.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/getCommentById/{id}")
    public Comment findById(@PathVariable String id){
        return commentService.getCommentById(id);
    }

    @GetMapping("/getAllComments")
    public List<Comment> getAllComments(){
        return commentService.getAllComments();
    }

    @PostMapping("/addComment")
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment){
        return new ResponseEntity<Comment>(commentService.addComment(comment), HttpStatus.CREATED);
    }

    // Get Comments according to posts
    @GetMapping("/getCommentByPostId/{postId}")
    public List<Comment> getCommentByPostId(@PathVariable String postId){
        return commentService.getCommentByPostId(postId);
    }

    @PostMapping("/addLikesComments/{userId}/{commentId}")
    public ResponseEntity<?> addLikes(@PathVariable String userId, @PathVariable String commentId){
        try {
            Comment comment = commentService.addLikes(userId, commentId);
            return ResponseEntity.ok(comment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add likes: " + e.getMessage());
        }
    }

    @DeleteMapping("/removeLikesComments/{userId}/{commentId}")
    public ResponseEntity<?> removeLikes(@PathVariable String userId, @PathVariable String commentId){
        try {
            Comment comment = commentService.removeLikes(userId, commentId);
            return ResponseEntity.ok(comment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove likes: " + e.getMessage());
        }
    }
}
