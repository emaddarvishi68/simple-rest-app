package com.emad.simplerestapp.service.initializer;

import com.emad.simplerestapp.model.Post;
import com.emad.simplerestapp.model.Todo;
import com.emad.simplerestapp.service.api.PostService;
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
public class PostWriter implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PostService postService;

    public PostWriter(PostService postService) {
        this.postService = postService;
    }

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Post>> typeReference = new TypeReference<List<Post>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/static/posts.json");
        try {
            List<Post> postList = mapper.readValue(inputStream,typeReference);
            postService.save(postList);
            logger.info("posts was saved");
        } catch (IOException e){
            logger.error("Unable to save posts: " + e.getMessage());
        }
    }
}
