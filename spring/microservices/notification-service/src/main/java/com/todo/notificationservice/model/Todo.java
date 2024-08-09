package com.todo.notificationservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "todos")
public class Todo {
    @Id
    private String id;

    private String title;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Status status;

    private int effortRequired;
    private String userId;
    private String category;
    private LocalDateTime reminderDate;
    private String eventName;
    // Custom validation for endDate
    @Transient
    @JsonIgnore
    public boolean endDateValid;
}