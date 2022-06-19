package com.emad.simplerestapp.service.api;

import com.emad.simplerestapp.exception.MasterEntityNotFoundException;
import com.emad.simplerestapp.model.Todo;

import java.io.IOException;
import java.util.List;

public interface TodoService {

    /**
     * save list of Todo
     *
     * @param todoList
     * @return Iterable<Todo>
     */
    Iterable<Todo> save(List<Todo> todoList);

    /**
     * get all todos
     *
     * @return List<Todo>
     */
    List<Todo> getAllTodos();

    /**
     * get todos by user id and completed
     *
     * @param userId
     * @param completed
     * @return List<Todo>
     * @throws MasterEntityNotFoundException
     */
    List<Todo> getTodosByUserIdAndCompleted(Integer userId, Boolean completed) throws MasterEntityNotFoundException;

    /**
     * fetch todos from resource
     *
     * @param resource
     * @return List<Todo>
     * @throws IOException
     */
    List<Todo> fetchFromResource(String resource) throws IOException;
}
