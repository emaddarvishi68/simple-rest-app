package com.emad.simplerestapp.repository;

import com.emad.simplerestapp.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> getTodosByUserIdAndCompleted(Integer userId, Boolean completed);
}
