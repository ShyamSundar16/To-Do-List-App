// src/main/java/com/taskmanagement/taskservice/controller/TodoController.java
package com.taskmanagement.taskservice.controller;

import com.taskmanagement.taskservice.model.Todo;
import com.taskmanagement.taskservice.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo) {
        Todo savedTodo = todoService.addTodo(todo);
        return ResponseEntity.ok(savedTodo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable String id, @RequestBody Todo todoDetails) {
        Optional<Todo> updatedTodo = todoService.updateTodo(id, todoDetails);
        return updatedTodo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable String id) {
        Optional<Todo> todo = todoService.getTodoById(id);
        return todo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable String id) {
        todoService.deleteTodoById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Todo>> getTodosByUserId(
            @PathVariable String userId,
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) String category) {
        List<Todo> todos = todoService.findByUserIdAndFilters(userId, completed, category);
        return ResponseEntity.ok(todos);
    }

}