package com.taskmanagement.taskservice.handlers;

import com.taskmanagement.taskservice.model.Todo;
import com.taskmanagement.taskservice.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoQueryHandler {
    @Autowired
    private TodoService todoQueryService;

    public List<Todo> handleGetAllTodosQuery() {
        return todoQueryService.getAllTodos();
    }

    public Optional<Todo> handleGetTodoByIdQuery(String id) {
        return todoQueryService.getTodoById(id);
    }
}
