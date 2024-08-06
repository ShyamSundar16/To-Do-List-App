package com.taskmanagement.taskservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
    private LocalDateTime dueDate;
    private boolean completed;
    private String userId;

    @DBRef
    private Category category;

    private LocalDateTime reminderDate;
    private String repeatInterval; // e.g., "DAILY", "WEEKLY", "MONTHLY"

}