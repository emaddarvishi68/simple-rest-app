package com.emad.simplerestapp.service.initializer;

import com.emad.simplerestapp.model.Comment;
import com.emad.simplerestapp.model.Post;
import com.emad.simplerestapp.service.api.CommentService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class CommentWriter implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CommentService commentService;

    public CommentWriter(CommentService commentService) {
        this.commentService = commentService;
    }
    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Comment>> typeReference = new TypeReference<List<Comment>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/static/comments.json");
        try {
            List<Comment> postList = mapper.readValue(inputStream,typeReference);
            commentService.save(postList);
            logger.info("comments was saved");
        } catch (IOException e){
            logger.error("Unable to save comments: " + e.getMessage());
        }
    }
}
