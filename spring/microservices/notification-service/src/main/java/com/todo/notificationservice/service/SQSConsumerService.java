package com.todo.notificationservice.service;

import com.todo.notificationservice.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Service
public class SQSConsumerService {

    @Autowired
    private NotificationService notificationService;

    @SqsListener("TaskStatus.fifo")
    public void loadMessageFromSQS(Todo todo)  {
        notificationService.sendReminder(todo.getUserId(), "Task notification",todo.getEventName()+ " task for : " + todo.getTitle());

    }

}
