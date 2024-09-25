package com.taskmanagement.taskservice.mysql;

import com.taskmanagement.taskservice.model.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Entity
public class Todo {
    @Id
    private String id;

    @NotNull(message = "Task name is mandatory")
    private String title;

    @NotNull(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Task start date is mandatory")
    private Date startDate;

    @NotNull(message = "Task end date is mandatory")
    private Date endDate;

    @NotNull(message = "Task status is mandatory")
    private Status status;

    @Min(value = 1, message = "Effort required must be greater than 0")
    private int effortRequired;
    private String userId;
    private String category;
    private Date reminderDate;
    private Boolean isFavorite;

    @Transient
    private String eventName;
}