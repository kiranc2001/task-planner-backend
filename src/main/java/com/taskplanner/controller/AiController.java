package com.taskplanner.controller;



import com.taskplanner.dto.AiSuggestionRequestDto;
import com.taskplanner.dto.AiSuggestionResponseDto;
import com.taskplanner.service.AiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/suggestions/{userId}")
@CrossOrigin(origins = "*")
public class AiController {
    @Autowired
    private AiService aiService;

    @PostMapping
    public AiSuggestionResponseDto getSuggestions(@PathVariable Long userId, @Valid @RequestBody AiSuggestionRequestDto dto) {
        dto.setUserId(userId);
        return aiService.getSuggestions(dto);
    }
}
