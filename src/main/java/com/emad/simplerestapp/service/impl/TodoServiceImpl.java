package com.emad.simplerestapp.service.impl;

import com.emad.simplerestapp.model.Todo;
import com.emad.simplerestapp.repository.TodoRepository;
import com.emad.simplerestapp.service.api.TodoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final ObjectMapper objectMapper;

    public TodoServiceImpl(TodoRepository todoRepository, ObjectMapper objectMapper) {
        this.todoRepository = todoRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Iterable<Todo> save(List<Todo> todoList) {
        return todoRepository.saveAll(todoList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Todo> getTodosByUserIdAndCompleted(Integer userId, Boolean completed) {
        return todoRepository.getTodosByUserIdAndCompleted(userId, completed);
    }

    @Override
    public List<Todo> fetchFromResource(String resource) throws IOException {
        TypeReference<List<Todo>> typeReference = new TypeReference<List<Todo>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream(resource);
        return objectMapper.readValue(inputStream, typeReference);
    }
}
