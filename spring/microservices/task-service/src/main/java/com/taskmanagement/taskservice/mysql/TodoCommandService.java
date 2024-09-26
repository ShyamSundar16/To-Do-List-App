package com.taskmanagement.taskservice.mysql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class TodoCommandService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
    @Autowired
    private TodoMySQLRepository todoRepository;

//    @Autowired
//    private QueueMessagingTemplate queueMessagingTemplate;
//
//    @Value("${cloud.aws.end-point.uri}")
//    private String endpoint;
    private static final String TOPIC = "todo-events";

    @CachePut(value = "todos", key = "#todo.id")
    public Todo addTodo(Todo todo) {
        notifyUser(todo, "CREATED");
        todo.setId(todo.getUserId());
        return todoRepository.save(todo);
    }


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


    @Cacheable(value = "todos")
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }


    @Cacheable(value = "todos", key = "#id")
    public Optional<Todo> getTodoById(String id) {
        return todoRepository.findById(id);
    }


    @CacheEvict(value = "todos", key = "#id")
    public void deleteTodoById(String id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
        }
    }


    private void notifyUser(Todo todo, String eventName) {
        try {
            todo.setEventName(eventName);
            String todoJson = objectMapper.writeValueAsString(todo);
            kafkaTemplate.send(TOPIC, todoJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert Todo to JSON", e);
        }
    }
}