package com.emad.simplerestapp.service.initializer;

import com.emad.simplerestapp.service.api.TodoService;
import com.emad.simplerestapp.staticvalues.ResourceName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TodoWriter implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TodoService todoService;

    public TodoWriter(TodoService todoService) {
        this.todoService = todoService;
    }

    @Override
    public void run(String... args) throws Exception {
        todoService.save(todoService.fetchFromResource(ResourceName.TODOS_RESOURCE));
        logger.info("todos was saved on db");
    }
}
