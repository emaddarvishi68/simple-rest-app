package com.emad.simplerestapp.service.initializer;

import com.emad.simplerestapp.service.api.CommentService;
import com.emad.simplerestapp.service.impl.ResourceName;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * read from comments resource and write to db
 */
@Component
public class CommentWriter implements CommandLineRunner {
    private final CommentService commentService;
    public CommentWriter(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public void run(String... args) throws Exception {
        commentService.save(commentService.fetchFromResource(ResourceName.COMMENTS_RESOURCE));
    }

}
