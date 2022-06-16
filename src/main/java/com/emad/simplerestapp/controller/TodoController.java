package com.emad.simplerestapp.controller;

import com.emad.simplerestapp.service.api.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TodoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }
}
