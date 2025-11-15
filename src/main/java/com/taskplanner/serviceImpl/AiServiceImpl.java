package com.taskplanner.serviceImpl;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletionMessageParam;

import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

import com.openai.models.chat.completions.ChatCompletionUserMessageParam;
import com.taskplanner.dto.AiSuggestionRequestDto;
import com.taskplanner.dto.AiSuggestionResponseDto;
import com.taskplanner.service.AiService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiServiceImpl implements AiService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Override
    public AiSuggestionResponseDto getSuggestions(AiSuggestionRequestDto dto) {

        // Create OpenAI client
        OpenAIClient client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();

        // Build the task description
        StringBuilder tasksDesc = new StringBuilder("User tasks:\n");
        if (dto.getTasks() != null) {
            dto.getTasks().forEach(task -> tasksDesc.append("- ")
                    .append(task.getTitle())
                    .append(" (Priority: ").append(task.getPriority())
                    .append(", Due: ").append(task.getDueDate())
                    .append(")\n"));
        }

        String prompt =
                "You are an intelligent task planner. Analyze the tasks below and generate an optimized daily plan. "
                        + "Include priorities, suggested time blocks, workload balancing, and productivity recommendations.\n\n"
                        + tasksDesc;

        ChatCompletionUserMessageParam userMessage =
                ChatCompletionUserMessageParam.builder()
                        .content(prompt)
                        .build();

        ChatCompletionCreateParams params =
                ChatCompletionCreateParams.builder()
                        .model(ChatModel.GPT_4O_MINI)
                        .addMessage(userMessage)
                        .maxTokens(400)
                        .temperature(0.7)
                        .build();


        // Send request
        ChatCompletion response = client.chat().completions().create(params);

        // Extract output text
        String content = response.choices().get(0).message().content().stream()
                .reduce("", (a, b) -> a + b + "\n")
                .trim();

        // Build response DTO
        AiSuggestionResponseDto result = new AiSuggestionResponseDto();
        result.setRecommendations(content);

        return result;
    }
}
