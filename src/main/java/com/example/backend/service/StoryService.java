package com.example.backend.service;

import com.example.backend.model.DatabaseSequence;
import com.example.backend.model.Story;
import com.example.backend.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class StoryService {

    @Autowired
    private StoryRepository storyRepository;

        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");


    public List<Story> getAllStories(){
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24);

        return storyRepository.findAll().stream()
                .filter(story -> LocalDateTime.parse(story.getDate(), DATE_FORMATTER).isAfter(cutoffTime))
                .collect(Collectors.toList());
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

    public boolean watchedStory(String id){
        Optional<Story> optionalStory = storyRepository.findById(id);

        if (optionalStory.isEmpty()) {
            throw new IllegalArgumentException("Story with ID " + id + " does not exist.");
        }

        Story story = optionalStory.get();
        story.setWatched(true);
        storyRepository.save(story);

        return true;
    }

    public String deleteStory(String id){
        storyRepository.findById(id).orElseThrow(
            () -> new RuntimeException(id)
        );

        storyRepository.deleteById(id);
        return "Story deleted successfully";
    }
}
