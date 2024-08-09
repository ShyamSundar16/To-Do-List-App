package com.todo.notificationservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.notificationservice.model.Todo;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Service
public class KafkaConsumerService {


    @Autowired
    private NotificationService notificationService;

    private KafkaConsumer<String, String> consumer;

    ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    @PostConstruct
    public void init() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "todo-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "10");

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("todo-events"));

        new Thread(this::pollMessages).start();
    }

    private void pollMessages() {
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    // Process each message
                    try {
                        Todo todo = objectMapper.readValue(record.value(), Todo.class);
                        notificationService.sendReminder(todo.getUserId(), "Task notification",todo.getEventName()+ " task for : " + todo.getTitle());
                    } catch (Exception e) {
                        log.error("Error consuming message", e);
                    }
                }
                consumer.commitSync();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }
}