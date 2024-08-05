package com.taskmanagement.taskservice.service;

import com.taskmanagement.taskservice.model.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoService {
    Todo addTodo(Todo todo);
    Optional<Todo> updateTodo(String id, Todo todoDetails);
    List<Todo> getAllTodos();
    Optional<Todo> getTodoById(String id);
    void deleteTodoById(String id);
    List<Todo> findByUserId(String userId);
    List<Todo> findByCategoryId(String categoryId);
    List<Todo> findByCompleted(boolean completed);
}