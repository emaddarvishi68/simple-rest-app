package com.emad.simplerestapp.service.initializer;

import com.emad.simplerestapp.model.Todo;
import com.emad.simplerestapp.service.api.TodoService;
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
public class TodoWriter implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TodoService todoService;

    public TodoWriter(TodoService todoService) {
        this.todoService = todoService;
    }
    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Todo>> typeReference = new TypeReference<List<Todo>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/static/todos.json");
        try {
            List<Todo> todoList = mapper.readValue(inputStream,typeReference);
            todoService.save(todoList);
            logger.info("todos was saved");
        } catch (IOException e){
            logger.error("Unable to save todos: " + e.getMessage());
        }
    }
}
