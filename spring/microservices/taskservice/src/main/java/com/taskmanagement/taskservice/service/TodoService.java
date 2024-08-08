package com.taskmanagement.taskservice.service;

import com.taskmanagement.taskservice.model.Status;
import com.taskmanagement.taskservice.model.Todo;
import java.util.List;
import java.util.Optional;

public interface TodoService {
    Todo addTodo(Todo todo);
    Optional<Todo> updateTodo(String id, Todo todoDetails);
    List<Todo> getAllTodos();
    Optional<Todo> getTodoById(String id);
    void deleteTodoById(String id);
    List<Todo> findByUserIdAndFilters(String userId, Status status, String category);
}