package com.taskplanner.dto;

import lombok.Data;

@Data
public class TaskAnalyticsDto {
    private Long totalTasks;
    private Long completedTasks;
    private Long pendingTasks;
    private Double completionPercentage;
}
