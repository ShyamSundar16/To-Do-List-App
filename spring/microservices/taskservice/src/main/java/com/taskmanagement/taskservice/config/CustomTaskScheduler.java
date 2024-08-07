// src/main/java/com/taskmanagement/taskservice/scheduler/TaskScheduler.java
package com.taskmanagement.taskservice.config;

import com.taskmanagement.taskservice.model.Todo;
import com.taskmanagement.taskservice.repository.TodoRepository;
import com.taskmanagement.taskservice.service.NotificationService;
import com.taskmanagement.taskservice.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class CustomTaskScheduler {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private TodoService todoService;

    @Scheduled(fixedRate = 3600000) // Check every hour
    public void checkReminders() {
        List<Todo> todos = todoRepository.findAll();
        for (Todo todo : todos) {
            if (todo.getReminderDate() != null && todo.getReminderDate().toLocalDate().isEqual(LocalDate.now()) && !todo.isCompleted()) {
                notificationService.sendReminder(todo.getUserId(), "Reminder for task: " + todo.getTitle());
                todo.setReminderDate(null); // Clear reminder date after sending notification
                // Update due date if repeat interval is set
                if (todo.getRepeatInterval() != null) {
                    todo.setDueDate(todoService.computeNextDueDate(todo));
                }
                todoRepository.save(todo);
            }
        }
    }
}