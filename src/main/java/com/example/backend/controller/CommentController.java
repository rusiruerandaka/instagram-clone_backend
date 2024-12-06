package com.example.backend.controller;

import com.example.backend.model.Comment;
import com.example.backend.model.Post;
import com.example.backend.service.CommentService;
import com.example.backend.service.PostService;
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
}
