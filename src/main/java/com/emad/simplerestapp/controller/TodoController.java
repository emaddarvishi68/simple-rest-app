package com.emad.simplerestapp.controller;

import com.emad.simplerestapp.controller.api.ControllersCommonMethods;
import com.emad.simplerestapp.model.Todo;
import com.emad.simplerestapp.service.api.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController extends ControllersCommonMethods<Todo> {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Todo>> getAllTodos() {
        return returnResponseEntity(todoService.getAllTodos());
    }

    @GetMapping(params = {"userId", "completed"}, consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Todo>> getTodosByUserIdAndCompleted(@RequestParam(defaultValue = "0") Integer userId, @RequestParam(defaultValue = "true") Boolean completed) {
        return returnResponseEntity(todoService.getTodosByUserIdAndCompleted(userId, completed));
    }

}
