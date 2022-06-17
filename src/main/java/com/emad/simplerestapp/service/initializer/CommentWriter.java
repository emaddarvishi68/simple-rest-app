package com.emad.simplerestapp.service.initializer;

import com.emad.simplerestapp.service.api.CommentService;
import com.emad.simplerestapp.staticvalues.ResourceName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class CommentWriter implements CommandLineRunner {
    private final CommentService commentService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CommentWriter(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public void run(String... args) throws Exception { //todo handle exception in controllerAdvice if file not found I/O Exception
        commentService.save(commentService.fetchFromResource(ResourceName.COMMENTS_RESOURCE));
        logger.info("comments was saved on db");//todo logs and delete logger from writers
    }

}
