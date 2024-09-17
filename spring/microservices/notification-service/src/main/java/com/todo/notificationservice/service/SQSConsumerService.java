package com.todo.notificationservice.service;

import com.todo.notificationservice.model.Todo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SQSConsumerService {

    @Autowired
    private NotificationService notificationService;

    @Value("${cloud.aws.sqs.queue.name}")
    private String queueName;

    @SqsListener("${cloud.aws.sqs.queue.name}")
    public void loadMessageFromSQS(Todo todo)  {
        log.info("Message from SQS: " + todo);
        notificationService.sendReminder(todo.getUserId(), "Task notification",todo.getEventName()+ " task for : " + todo.getTitle());

    }

}
