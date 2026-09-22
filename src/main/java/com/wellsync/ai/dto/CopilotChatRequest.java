package com.wellsync.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CopilotChatRequest {
    @NotNull(message = "Well ID is required")
    private UUID wellId;

    @NotBlank(message = "Message is required")
    private String message;
}
