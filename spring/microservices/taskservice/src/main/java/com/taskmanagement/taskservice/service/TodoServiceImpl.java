// src/main/java/com/taskmanagement/taskservice/service/impl/TodoServiceImpl.java
package com.taskmanagement.taskservice.service;

import com.taskmanagement.taskservice.model.Todo;
import com.taskmanagement.taskservice.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Override
    @CachePut(value = "todos", key = "#todo.id")
    public Todo addTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    @CachePut(value = "todos", key = "#id")
    public Optional<Todo> updateTodo(String id, Todo todoDetails) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            todo.setTitle(todoDetails.getTitle());
            todo.setDescription(todoDetails.getDescription());
            todo.setDueDate(todoDetails.getDueDate());
            todo.setCompleted(todoDetails.isCompleted());
            todo.setReminderDate(todoDetails.getReminderDate());
            todo.setRepeatInterval(todoDetails.getRepeatInterval());
            todo.setUser(todoDetails.getUser());
            todo.setCategory(todoDetails.getCategory());
            return Optional.of(todoRepository.save(todo));
        }
        return Optional.empty();
    }

    @Override
    @Cacheable(value = "todos")
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    @Cacheable(value = "todos", key = "#id")
    public Optional<Todo> getTodoById(String id) {
        return todoRepository.findById(id);
    }

    @Override
    @CacheEvict(value = "todos", key = "#id")
    public void deleteTodoById(String id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
        }
    }

    @Override
    @Cacheable(value = "todos", key = "#userId")
    public List<Todo> findByUserId(String userId) {
        return todoRepository.findByUserId(userId);
    }

    @Override
    @Cacheable(value = "todos", key = "#categoryId")
    public List<Todo> findByCategoryId(String categoryId) {
        return todoRepository.findByCategoryId(categoryId);
    }

    @Override
    @Cacheable(value = "todos", key = "#completed")
    public List<Todo> findByCompleted(boolean completed) {
        return todoRepository.findByCompleted(completed);
    }
}