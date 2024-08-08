// src/main/java/com/taskmanagement/taskservice/scheduler/TaskScheduler.java
package com.taskmanagement.taskservice.config;

import com.taskmanagement.taskservice.model.Status;
import com.taskmanagement.taskservice.model.Todo;
import com.taskmanagement.taskservice.repository.TodoRepository;
import com.taskmanagement.taskservice.service.NotificationService;
import com.taskmanagement.taskservice.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CustomTaskScheduler {

    private static final Logger log = LoggerFactory.getLogger(CustomTaskScheduler.class);
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private TodoService todoService;

    @Scheduled(fixedRate = 3600000) // Check every hour
    public void checkReminders() {
        List<Todo> todos = todoRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        for (Todo todo : todos) {
            if (((todo.getReminderDate() != null && todo.getReminderDate().toLocalDate().isEqual(now.toLocalDate())) ||(todo.getEndDate().isEqual(now)))
                    && !Status.COMPLETED.equals(todo.getStatus())) {
                log.info("Sending reminder for task");
                notificationService.sendReminder(todo.getUserId(), "Reminder for task: " + todo.getTitle());
                todo.setReminderDate(null); // Clear reminder date after sending notification
                todoRepository.save(todo);
            }
        }
    }
}