package com.taskplanner.controller;

import com.taskplanner.dto.*;
import com.taskplanner.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks/{userId}")  // Class-level: /api/tasks/{userId}/...
@CrossOrigin(origins = "*")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public TaskResponseDto create(@PathVariable Long userId, @Valid @RequestBody TaskCreateDto dto) {
        return taskService.createTask(userId, dto);
    }

    @GetMapping
    public List<TaskResponseDto> getAll(@PathVariable Long userId) {
        return taskService.getTasksByUser(userId);
    }

    @GetMapping("/{id}")
    public TaskResponseDto get(@PathVariable Long userId, @PathVariable Long id) {
        return taskService.getTask(id, userId);
    }

    @PutMapping("/{id}")
    public TaskResponseDto update(@PathVariable Long userId, @PathVariable Long id, @Valid @RequestBody TaskUpdateDto dto) {
        return taskService.updateTask(id, userId, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long id) {
        taskService.deleteTask(id, userId);
        return ResponseEntity.ok().build();
    }
}
