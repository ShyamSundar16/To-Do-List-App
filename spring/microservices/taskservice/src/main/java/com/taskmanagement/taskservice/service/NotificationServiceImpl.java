package com.taskmanagement.taskservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendReminder(String userId, String message) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(userId); // Assuming userId is the email address
            helper.setSubject("Task Reminder");
            helper.setText(message, true); // true indicates HTML content
            emailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}