package com.emad.simplerestapp.controller;

import com.emad.simplerestapp.controller.api.ControllersCommonMethods;
import com.emad.simplerestapp.exception.MasterEntityNotFoundException;
import com.emad.simplerestapp.model.Todo;
import com.emad.simplerestapp.model.dto.TodoDto;
import com.emad.simplerestapp.service.api.TodoService;
import com.emad.simplerestapp.service.assemblers.TodoAssembler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * todo controller
 */
@Api(value = "Todo")
@RestController
@RequestMapping("/todos")
public class TodoController extends ControllersCommonMethods<Todo> {
    private final TodoService todoService;
    private final TodoAssembler todoAssembler;

    public TodoController(TodoService todoService, TodoAssembler todoAssembler) {
        this.todoService = todoService;
        this.todoAssembler = todoAssembler;
    }

    @ApiOperation(value = "Get all to-do’s", response = ResponseEntity.class)
    @GetMapping(consumes = "application/json", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<TodoDto>> getAllTodos() {
        List<Todo> allTodos = todoService.getAllTodos();
        return allTodos.isEmpty()
                ? new ResponseEntity<>( HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(todoAssembler.toCollectionModel(allTodos), HttpStatus.OK);
    }

    @ApiOperation(value = "Get to-do’s of specific user by user id and completed field", response = ResponseEntity.class)
    @GetMapping(params = {"userId", "completed"}, consumes = "application/json", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<TodoDto>> getTodosByUserIdAndCompleted(@RequestParam(defaultValue = "0") Integer userId, @RequestParam(defaultValue = "true") Boolean completed) throws MasterEntityNotFoundException {
        List<Todo> todosByUserIdAndCompleted = todoService.getTodosByUserIdAndCompleted(userId, completed);
        return todosByUserIdAndCompleted.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(todoAssembler.toCollectionModel(todosByUserIdAndCompleted), HttpStatus.OK);
    }

}
