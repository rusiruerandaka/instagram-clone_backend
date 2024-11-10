package com.example.backend.service;

import com.example.backend.model.DatabaseSequence;
import com.example.backend.model.Story;
import com.example.backend.repository.StoryRepository;
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
public class StoryService {

    @Autowired
    private StoryRepository storyRepository;

    public List<Story> getAllStories(){
        return storyRepository.findAll();
    }

    public Story getStoryById(String id){
        return storyRepository.findById(id).orElse(null);
    }

    public Story addStory(Story story){
        story.setStoryId(generateSequence(Story.SEQUENCE_NAME));
        return storyRepository.save(story);
    }

    @Autowired
    private MongoOperations mongoOperations;

    public String generateSequence(String seqName){
        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return "S" + (!Objects.isNull(counter) ? counter.getSeq() : 1);
    }
}
