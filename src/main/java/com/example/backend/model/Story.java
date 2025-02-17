package com.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Story {

    @Transient
    public static final String SEQUENCE_NAME="story_sequence";

    @Id
    private String storyId;

    private String userId;

    public String imageUrl;
    public int likeCount;
    private List<String> watchedUsers = new ArrayList<>();    
    private String description;
    private String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
}
