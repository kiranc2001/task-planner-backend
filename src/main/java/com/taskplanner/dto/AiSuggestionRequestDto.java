package com.taskplanner.dto;

import lombok.Data;
import java.util.List;

@Data
public class AiSuggestionRequestDto {
    private Long userId;
    private List<TaskResponseDto> tasks;
}
