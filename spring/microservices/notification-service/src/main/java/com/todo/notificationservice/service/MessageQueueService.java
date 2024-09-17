package com.todo.notificationservice.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.QueueDoesNotExistException;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.notificationservice.model.Todo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageQueueService {
    @Value("${cloud.aws.sqs.queue.TaskStatus.name}")
    private String messageQueueTopic;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private final AmazonSQS amazonSQSClient;

    @Autowired
    NotificationService notificationService;

    @Scheduled(fixedDelay = 1000) // executes on every 2 second gap.
    public void receiveMessages() {
        try {
            String queueUrl = amazonSQSClient.getQueueUrl(messageQueueTopic).getQueueUrl();
            log.info("Reading SQS Queue done: URL {}", queueUrl);
            ReceiveMessageResult receiveMessageResult = amazonSQSClient.receiveMessage(queueUrl);
            receiveMessageResult.getMessages().stream().forEach(message -> {
                try {
                    Todo todo = objectMapper.readValue(message.getBody(), Todo.class);
                    notificationService.sendReminder(todo.getUserId(), "Task notification", todo.getEventName() + " task for : " + todo.getTitle());
                } catch (JsonProcessingException e) {
                    log.error("Error parsing message body to Todo object", e);
                }
                amazonSQSClient.deleteMessage(queueUrl, message.getReceiptHandle());
            });
        } catch (QueueDoesNotExistException e) {
            log.error("Queue does not exist {}", e.getMessage());
        }
    }
}
