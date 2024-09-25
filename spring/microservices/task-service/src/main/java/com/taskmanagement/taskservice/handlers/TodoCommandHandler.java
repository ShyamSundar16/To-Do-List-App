package com.taskmanagement.taskservice.handlers;

import com.taskmanagement.taskservice.mysql.TodoCommandService;
import com.taskmanagement.taskservice.mysql.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoCommandHandler {
    @Autowired
    private TodoCommandService todoService;

    public Todo handleAddTodoCommand(Todo todo) {
        return todoService.addTodo(todo);
    }

    public void handleDeleteTodoCommand(String id) {
        todoService.deleteTodoById(id);
    }
}
