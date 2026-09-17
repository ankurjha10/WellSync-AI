package com.wellsync.ai.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CompletionRequest {
    @NotNull(message = "Well ID is required")
    private UUID wellId;

    private String completionType;
    private Double tubingDepth;
    private Double casingDepth;
    private Double perforationTop;
    private Double perforationBottom;
    private Double pumpSettingDepth;
}
