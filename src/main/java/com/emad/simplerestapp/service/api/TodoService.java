package com.emad.simplerestapp.service.api;

import com.emad.simplerestapp.model.Todo;

import java.io.IOException;
import java.util.List;

public interface TodoService {
    Iterable<Todo> save(List<Todo> todoList);

    List<Todo> getAllTodos();

    List<Todo> getTodosByUserIdAndCompleted(Integer userId, Boolean completed);

    List<Todo> fetchFromResource(String resource) throws IOException;
}
