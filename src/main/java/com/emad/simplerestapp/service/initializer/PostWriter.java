package com.emad.simplerestapp.service.initializer;

import com.emad.simplerestapp.service.api.PostService;
import com.emad.simplerestapp.staticvalues.ResourceName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PostWriter implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PostService postService;

    public PostWriter(PostService postService) {
        this.postService = postService;
    }

    @Override
    public void run(String... args) throws Exception {
        postService.save(postService.fetchFromResource(ResourceName.POST_RESOURCE));
        logger.info("posts was saved on db");
    }
}
