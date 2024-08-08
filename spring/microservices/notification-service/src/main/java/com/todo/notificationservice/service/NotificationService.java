package com.todo.notificationservice.service;

public interface NotificationService {
    void sendReminder(String userId, String message);
}