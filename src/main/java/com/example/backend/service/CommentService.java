package com.example.backend.service;

import com.example.backend.model.Comment;
import com.example.backend.model.DatabaseSequence;
import com.example.backend.model.User;
import com.example.backend.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment getCommentById(String id){
        return commentRepository.findById(id).orElse(null);
    }

    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    public Comment addComment(Comment comment){
        comment.setCommentId(generateSequence(Comment.SEQUENCE_NAME));
        return commentRepository.save(comment);
    }

    public Comment updateComment(Comment comment, String id){
        Comment existingComment = commentRepository.findById(comment.getCommentId()).orElseThrow(
                () -> new RuntimeException("Comment not found")
        );

        existingComment.setUserId(comment.getUserId() != null && !comment.getUserId().isEmpty() ? comment.getUserId() : existingComment.getUserId());
        existingComment.setCommentId(comment.getCommentId() != null ? comment.getCommentId() : existingComment.getCommentId());
        existingComment.setContent(comment.getContent() != null && !comment.getContent().isEmpty() ? comment.getContent() : existingComment.getContent());

        return existingComment;
    }

    public void deletePost(String id){
        commentRepository.findById(id).orElseThrow(
                () -> new RuntimeException(id)
        );

        commentRepository.deleteById(id);
    }

    public List<Comment> getCommentByPostId(String postId){
        return commentRepository.getCommentByPostId(postId);
    }

    public Comment addLikes(String userId, String commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
            () -> new RuntimeException()
        );
        comment.getLikedUsers().add(userId);
        return commentRepository.save(comment);
    }

    public Comment removeLikes(String userId, String commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
            () -> new RuntimeException()
        );
        comment.getLikedUsers().remove(userId);
        return commentRepository.save(comment);
    }

    @Autowired
    private MongoOperations mongoOperations;

    public String generateSequence(String seqName){
        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return "C" + (!Objects.isNull(counter) ? counter.getSeq() : 1);
    }
}
