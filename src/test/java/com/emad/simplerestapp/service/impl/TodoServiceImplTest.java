package com.emad.simplerestapp.service.impl;

import com.emad.simplerestapp.exception.MasterEntityNotFoundException;
import com.emad.simplerestapp.model.Comment;
import com.emad.simplerestapp.model.Todo;
import com.emad.simplerestapp.model.User;
import com.emad.simplerestapp.repository.CommentRepository;
import com.emad.simplerestapp.repository.TodoRepository;
import com.emad.simplerestapp.service.api.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class TodoServiceImplTest {

    @InjectMocks
    TodoServiceImpl todoService;
    @Mock
    TodoRepository todoRepository;
    @Mock
    UserService userService;

    private List<Todo> getTodoList() {
        Todo todo1 = Todo.builder().id(1).completed(true).title("todo1").userId(1).build();
        Todo todo2 = Todo.builder().id(2).completed(true).title("todo2").userId(2).build();
        Todo todo3 = Todo.builder().id(3).completed(true).title("todo3").userId(3).build();
        return Arrays.asList(todo1, todo2, todo3);
    }

    private User getUser(){
        return User.builder().id(1).build();
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveTest() {
        List<Todo> todoList = getTodoList();
        when(todoRepository.saveAll(todoList)).thenReturn(todoList);
        List<Todo> todoList1 = (List<Todo>) todoService.save(todoList);
        assertFalse(todoList1.isEmpty());
    }

    @Test
    public void getAllTodosTest(){
        when(todoRepository.findAll()).thenReturn(getTodoList());
        List<Todo> todoList1 = todoService.getAllTodos();
        assertFalse(todoList1.isEmpty());
    }

    @Test
    public void getTodosByUserIdAndCompletedTest1() throws MasterEntityNotFoundException {
        Optional<User> user = Optional.of(getUser());
        when(userService.getUserById(anyInt())).thenReturn(user);
        when(todoRepository.getTodosByUserIdAndCompleted(user.get().getId(), true)).thenReturn(getTodoList());
        List<Todo> todosByUserIdAndCompleted = todoService.getTodosByUserIdAndCompleted(user.get().getId(), true);
        assertFalse(todosByUserIdAndCompleted.isEmpty());
    }

    @Test
    public void getTodosByUserIdAndCompletedTest2() throws MasterEntityNotFoundException {
        Optional<User> user = Optional.of(getUser());
        when(userService.getUserById(anyInt())).thenReturn(Optional.empty());
        assertThrows(MasterEntityNotFoundException.class,()->{
            todoService.getTodosByUserIdAndCompleted(user.get().getId(),true);
        });
    }

}
