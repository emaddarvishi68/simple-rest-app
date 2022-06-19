package com.emad.simplerestapp.service.initializer;

import com.emad.simplerestapp.service.api.TodoService;
import com.emad.simplerestapp.service.impl.ResourceName;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * read from todos resource and write to db
 */
@Component
public class TodoWriter implements CommandLineRunner {
    private final TodoService todoService;

    public TodoWriter(TodoService todoService) {
        this.todoService = todoService;
    }

    @Override
    public void run(String... args) throws Exception {
        todoService.save(todoService.fetchFromResource(ResourceName.TODOS_RESOURCE));
    }
}
