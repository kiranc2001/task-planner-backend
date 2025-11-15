package com.taskplanner.serviceImpl;


import com.taskplanner.dto.*;
import com.taskplanner.exception.TaskNotFoundException;
import com.taskplanner.exception.UserNotFoundException;
import com.taskplanner.model.Task;
import com.taskplanner.model.User;
import com.taskplanner.repository.TaskRepository;
import com.taskplanner.repository.UserRepository;
import com.taskplanner.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public TaskResponseDto createTask(Long userId, TaskCreateDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        Task task = new Task();
        task.setUser(user);
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority());
        task.setStatus(dto.getStatus());
        task.setDueDate(dto.getDueDate());
        task = taskRepository.save(task);
        return mapToResponse(task);
    }

    @Override
    public TaskResponseDto getTask(Long id, Long userId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));
        if (!task.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to task");
        }
        return mapToResponse(task);
    }

    @Override
    public List<TaskResponseDto> getTasksByUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        return taskRepository.findByUserId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponseDto updateTask(Long id, Long userId, TaskUpdateDto dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));
        if (!task.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to task");
        }
        Optional.ofNullable(dto.getTitle()).ifPresent(task::setTitle);
        Optional.ofNullable(dto.getDescription()).ifPresent(task::setDescription);
        Optional.ofNullable(dto.getPriority()).ifPresent(task::setPriority);
        Optional.ofNullable(dto.getStatus()).ifPresent(task::setStatus);
        Optional.ofNullable(dto.getDueDate()).ifPresent(task::setDueDate);
        task = taskRepository.save(task);
        return mapToResponse(task);
    }

    @Override
    public void deleteTask(Long id, Long userId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));
        if (!task.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to task");
        }
        taskRepository.delete(task);
    }

    private TaskResponseDto mapToResponse(Task task) {
        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(task.getId());
        dto.setUserId(task.getUser().getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setPriority(task.getPriority());
        dto.setStatus(task.getStatus());
        dto.setDueDate(task.getDueDate());
        dto.setCreatedAt(task.getCreatedAt());
        return dto;
    }
}