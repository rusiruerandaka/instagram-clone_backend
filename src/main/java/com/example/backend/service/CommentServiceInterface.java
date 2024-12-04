package com.example.backend.service;

import java.util.List;

import com.example.backend.model.Comment;

public interface CommentServiceInterface {
    public Comment getCommentById(String id);
    public List<Comment> getAllComments();
    public Comment addComment(Comment comment);
    public Comment updateComment(Comment comment, String id);
    public void deleteComment(String id);
    public String generateSequence(String seqName);
}