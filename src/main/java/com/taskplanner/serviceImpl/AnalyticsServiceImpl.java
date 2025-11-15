package com.taskplanner.serviceImpl;


import com.taskplanner.dto.TaskAnalyticsDto;
import com.taskplanner.exception.UserNotFoundException;
import com.taskplanner.repository.TaskRepository;
import com.taskplanner.repository.UserRepository;
import com.taskplanner.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public TaskAnalyticsDto getAnalytics(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        Long total = taskRepository.countTotalByUserId(userId);
        Long completed = taskRepository.countCompletedByUserId(userId);
        Long pending = taskRepository.countPendingByUserId(userId);
        Double percentage = total > 0 ? (double) completed / total * 100 : 0.0;

        TaskAnalyticsDto dto = new TaskAnalyticsDto();
        dto.setTotalTasks(total);
        dto.setCompletedTasks(completed);
        dto.setPendingTasks(pending);
        dto.setCompletionPercentage(percentage);
        return dto;
    }
}