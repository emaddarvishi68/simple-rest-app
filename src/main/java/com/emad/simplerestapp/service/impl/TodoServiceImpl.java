package com.emad.simplerestapp.service.impl;

import com.emad.simplerestapp.exception.MasterEntityNotFoundException;
import com.emad.simplerestapp.model.Todo;
import com.emad.simplerestapp.model.User;
import com.emad.simplerestapp.repository.TodoRepository;
import com.emad.simplerestapp.service.api.TodoService;
import com.emad.simplerestapp.service.api.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final ObjectMapper objectMapper;

    private final UserService userService;

    public TodoServiceImpl(TodoRepository todoRepository, ObjectMapper objectMapper, UserService userService) {
        this.todoRepository = todoRepository;
        this.objectMapper = objectMapper;
        this.userService = userService;
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
    public List<Todo> getTodosByUserIdAndCompleted(Integer userId, Boolean completed) throws MasterEntityNotFoundException {
        User user = userService.getUserById(userId).orElseThrow(() -> new MasterEntityNotFoundException("There is no user with id: " + userId));
        return todoRepository.getTodosByUserIdAndCompleted(user.getId(), completed);
    }

    @Override
    public List<Todo> fetchFromResource(String resource) throws IOException {
        TypeReference<List<Todo>> typeReference = new TypeReference<List<Todo>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream(resource);
        if (inputStream == null) {
            throw new FileNotFoundException("todos.json was not found");
        }
        return objectMapper.readValue(inputStream, typeReference);
    }
}
