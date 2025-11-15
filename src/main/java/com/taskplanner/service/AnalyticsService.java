package com.taskplanner.service;

import com.taskplanner.dto.TaskAnalyticsDto;

public interface AnalyticsService {
    TaskAnalyticsDto getAnalytics(Long userId);
}
