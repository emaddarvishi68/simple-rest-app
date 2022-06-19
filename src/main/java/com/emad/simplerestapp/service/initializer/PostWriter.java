package com.emad.simplerestapp.service.initializer;

import com.emad.simplerestapp.service.api.PostService;
import com.emad.simplerestapp.service.impl.ResourceName;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * read from posts resource and write to db
 */
@Component
public class PostWriter implements CommandLineRunner {

    private final PostService postService;

    public PostWriter(PostService postService) {
        this.postService = postService;
    }

    @Override
    public void run(String... args) throws Exception {
        postService.save(postService.fetchFromResource(ResourceName.POST_RESOURCE));
    }
}
