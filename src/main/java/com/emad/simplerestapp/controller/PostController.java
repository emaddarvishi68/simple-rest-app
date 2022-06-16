package com.emad.simplerestapp.controller;

import com.emad.simplerestapp.service.api.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PostController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
}
