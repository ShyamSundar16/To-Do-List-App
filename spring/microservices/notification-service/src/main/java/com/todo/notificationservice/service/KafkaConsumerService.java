package com.todo.notificationservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.todo.notificationservice.model.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);
    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "todo-events", groupId = "todo-group")
    public void consume(String message) {
        log.info("Message consumed");
        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        try {
            Todo todo = objectMapper.readValue(message, Todo.class);
            notificationService.sendReminder(todo.getUserId(), "Task notification",todo.getEventName()+ " task for : " + todo.getTitle());
        } catch (Exception e) {
            log.error("Error consuming message", e);
        }
    }
}