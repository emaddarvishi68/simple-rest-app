package com.emad.simplerestapp.service.api;

import com.emad.simplerestapp.model.Todo;

import java.util.List;

public interface TodoService {
    Iterable<Todo> save(List<Todo> todoList);
}
