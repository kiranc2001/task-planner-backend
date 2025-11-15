package com.taskplanner.service;

import com.taskplanner.dto.AiSuggestionRequestDto;
import com.taskplanner.dto.AiSuggestionResponseDto;

public interface AiService {
    AiSuggestionResponseDto getSuggestions(AiSuggestionRequestDto dto);
}
