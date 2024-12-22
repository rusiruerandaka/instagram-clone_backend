package com.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Comment {

    @Transient
    public static final String SEQUENCE_NAME="comment_sequence";

    @Id
    private String commentId;
    private String userId;
    private String postId;
    private String content;
    private String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
}
