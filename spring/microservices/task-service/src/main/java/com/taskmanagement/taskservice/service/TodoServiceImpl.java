package com.taskmanagement.taskservice.service;

import com.taskmanagement.taskservice.model.Status;
import com.taskmanagement.taskservice.model.Todo;
import com.taskmanagement.taskservice.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

    @Override
    @CachePut(value = "todos", key = "#todo.id")
    public Todo addTodo(Todo todo) {
        notifyUser(todo, "CREATED");
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
            todo.setStartDate(todoDetails.getStartDate());
            todo.setEndDate(todoDetails.getEndDate());
            todo.setStatus(todoDetails.getStatus());
            todo.setEffortRequired(todoDetails.getEffortRequired());
            todo.setReminderDate(todoDetails.getReminderDate());
            todo.setUserId(todoDetails.getUserId());
            todo.setCategory(todoDetails.getCategory());
            notifyUser(todo, "UPDATED");
            todo.setIsFavorite(todoDetails.getIsFavorite());
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
    public List<Todo> findByUserIdAndFilters(String userId, Status status, String category) {
        List<Todo> todos = todoRepository.findByUserId(userId);
        if (status != null) {
            todos = todos.stream().filter(todo -> status.equals(todo.getStatus()) ).collect(Collectors.toList());
        }
        if (category != null && !category.isEmpty()) {
            todos = todos.stream().filter(todo -> category.equals(todo.getCategory())).collect(Collectors.toList());
        }
        return todos;
    }

    private void notifyUser(Todo todo, String eventName) {
        todo.setEventName(eventName);
        try {
            Map<String, Object> headers = new HashMap<>();
            headers.put("message-group-id","defaultValue");
            headers.put("message-deduplication-id","defaultValue");
            queueMessagingTemplate.convertAndSend(endpoint, todo, headers);
        } catch (Exception e) {
            log.error("Failed to send message to the queue: " + e.getMessage());
        }
    }
}