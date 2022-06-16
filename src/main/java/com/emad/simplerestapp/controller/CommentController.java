package com.emad.simplerestapp.controller;

import com.emad.simplerestapp.service.api.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CommentController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
}
