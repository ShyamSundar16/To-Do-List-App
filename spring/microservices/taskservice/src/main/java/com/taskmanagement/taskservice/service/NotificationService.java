package com.taskmanagement.taskservice.service;

public interface NotificationService {
    void sendReminder(String userId, String message);
}