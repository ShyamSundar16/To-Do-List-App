package com.todo.notificationservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Document(collection = "todos")
public class Todo {
    @Id
    private String id;

    private String title;

    private String description;

    private Date startDate;

    private Date endDate;

    private Status status;

    private int effortRequired;
    private String userId;
    private String category;
    private Date reminderDate;
    private String eventName;
}