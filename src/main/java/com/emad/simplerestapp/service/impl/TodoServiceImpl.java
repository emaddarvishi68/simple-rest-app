package com.emad.simplerestapp.service.impl;

import com.emad.simplerestapp.model.Todo;
import com.emad.simplerestapp.repository.TodoRepository;
import com.emad.simplerestapp.service.api.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Iterable<Todo> save(List<Todo> todoList) {
        return todoRepository.saveAll(todoList);
    }
}
