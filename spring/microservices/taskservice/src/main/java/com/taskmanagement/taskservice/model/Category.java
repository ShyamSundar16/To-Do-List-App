package com.taskmanagement.taskservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Getter
@Setter
@Document(collection = "categories")
public class Category {
    @Id
    private String id;

    private String name;

    @DBRef
    private List<Todo> todos;
}