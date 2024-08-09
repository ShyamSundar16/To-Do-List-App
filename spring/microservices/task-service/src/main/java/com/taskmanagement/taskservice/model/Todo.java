package com.taskmanagement.taskservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "todos")
public class Todo {
    @Id
    private String id;

    @NotNull(message = "Task name is mandatory")
    private String title;

    @NotNull(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Task start date is mandatory")
    private LocalDateTime startDate;

    @NotNull(message = "Task end date is mandatory")
    private LocalDateTime endDate;

    @NotNull(message = "Task status is mandatory")
    private Status status;

    @Min(value = 1, message = "Effort required must be greater than 0")
    private int effortRequired;
    private String userId;
    private String category;
    private LocalDateTime reminderDate;

    @Transient
    @JsonIgnore
    private String eventName;

    // Custom validation for endDate
    @Transient
    public boolean isEndDateValid() {
        Boolean isValidDate = endDate.isAfter(startDate);
        if (reminderDate != null) {
            isValidDate = reminderDate.isBefore(endDate) || reminderDate.equals(endDate);
        }
        return isValidDate;
    }
}