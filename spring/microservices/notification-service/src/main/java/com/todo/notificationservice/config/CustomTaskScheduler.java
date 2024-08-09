package com.todo.notificationservice.config;


import com.todo.notificationservice.model.Status;
import com.todo.notificationservice.model.Todo;
import com.todo.notificationservice.repository.TodoRepository;
import com.todo.notificationservice.service.NotificationService;
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

    @Scheduled(fixedRate = 3600000) // Check every hour
    private void checkReminders() {
        List<Todo> todos = todoRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        for (Todo todo : todos) {
            if (((todo.getReminderDate() != null && todo.getReminderDate().toLocalDate().isEqual(now.toLocalDate())) ||(todo.getEndDate().isEqual(now)))
                    && !Status.COMPLETED.equals(todo.getStatus())) {
                log.info("Sending reminder for task");
                notificationService.sendReminder(todo.getUserId(), "Task Reminder","Reminder for task: " + todo.getTitle());
                todo.setReminderDate(null); // Clear reminder date after sending notification
                todoRepository.save(todo);
            }
        }
    }
}