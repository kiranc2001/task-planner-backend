package com.taskplanner.controller;


import com.taskplanner.dto.TaskAnalyticsDto;
import com.taskplanner.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics/tasks/{userId}")
@CrossOrigin(origins = "*")
public class AnalyticsController {
    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping
    public TaskAnalyticsDto getTaskAnalytics(@PathVariable Long userId) {
        return analyticsService.getAnalytics(userId);
    }
}