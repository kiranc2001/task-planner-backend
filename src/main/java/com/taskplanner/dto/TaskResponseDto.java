package com.taskplanner.dto;

import com.taskplanner.model.Priority;
import com.taskplanner.model.Status;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TaskResponseDto {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private Priority priority;
    private Status status;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
}
