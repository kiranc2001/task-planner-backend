package com.taskplanner.service;

import com.taskplanner.dto.*;
import java.util.List;

public interface TaskService {
    TaskResponseDto createTask(Long userId, TaskCreateDto dto);
    TaskResponseDto getTask(Long id, Long userId);
    List<TaskResponseDto> getTasksByUser(Long userId);
    TaskResponseDto updateTask(Long id, Long userId, TaskUpdateDto dto);
    void deleteTask(Long id, Long userId);
}
