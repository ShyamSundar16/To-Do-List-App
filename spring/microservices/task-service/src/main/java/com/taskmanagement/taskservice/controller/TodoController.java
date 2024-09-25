package com.taskmanagement.taskservice.controller;

import com.taskmanagement.taskservice.exception.TaskNotFoundException;
import com.taskmanagement.taskservice.handlers.TodoCommandHandler;
import com.taskmanagement.taskservice.handlers.TodoQueryHandler;
import com.taskmanagement.taskservice.model.Status;
import com.taskmanagement.taskservice.model.Todo;
import com.taskmanagement.taskservice.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/taskservice")
@Tag(name = "Todo Controller", description = "API for managing To-Do items")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoCommandHandler todoCommandHandler;

    @Autowired
    private TodoQueryHandler todoQueryHandler;

    @CrossOrigin
    @PostMapping("/add-list")
    @Operation(summary = "Add a new To-Do item", description = "Creates a new To-Do item and saves it to the database")
    public ResponseEntity<com.taskmanagement.taskservice.mysql.Todo> addTodo(@RequestBody com.taskmanagement.taskservice.mysql.Todo todo) {
        com.taskmanagement.taskservice.mysql.Todo savedTodo = todoCommandHandler.handleAddTodoCommand(todo);
        return ResponseEntity.ok(savedTodo);
    }

    @CrossOrigin
    @PutMapping("/update/{id}")
    @Operation(summary = "Update an existing To-Do item", description = "Updates the details of an existing To-Do item by its ID")
    public ResponseEntity<Todo> updateTodo(@PathVariable String id, @RequestBody Todo todoDetails) {
        Optional<Todo> updatedTodo = todoService.updateTodo(id, todoDetails);
        return updatedTodo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @GetMapping("/list/all")
    @Operation(summary = "Get all To-Do items", description = "Retrieves a list of all To-Do items")
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> todos = todoQueryHandler.handleGetAllTodosQuery();
        return ResponseEntity.ok(todos);
    }

    @CrossOrigin
    @GetMapping("/list/{id}")
    @Operation(summary = "Get a To-Do item by ID", description = "Retrieves a To-Do item by its ID")
    public ResponseEntity<Todo> getTodoById(@PathVariable String id) {
        Optional<Todo> todo = todoService.getTodoById(id);
        return todo.map(ResponseEntity::ok).orElseThrow(() -> new TaskNotFoundException("Task not found with Id: "+id));
    }

    @CrossOrigin
    @DeleteMapping("/list/{id}")
    @Operation(summary = "Delete a To-Do item by ID", description = "Deletes a To-Do item by its ID")
    public ResponseEntity<Void> deleteTodoById(@PathVariable String id) {
        todoService.deleteTodoById(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @GetMapping("/{userId}")
    @Operation(summary = "Get To-Do items by User ID", description = "Retrieves a list of To-Do items for a specific user, with optional filters for status and category")
    public ResponseEntity<List<Todo>> getTodosByUserId(
            @PathVariable String userId,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) String category) {
        List<Todo> todos = todoService.findByUserIdAndFilters(userId, status, category);
        return ResponseEntity.ok(todos);
    }

}