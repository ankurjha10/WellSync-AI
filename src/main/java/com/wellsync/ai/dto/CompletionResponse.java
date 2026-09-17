package com.wellsync.ai.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class CompletionResponse {
    private UUID id;
    private UUID wellId;
    private String completionType;
    private Double tubingDepth;
    private Double casingDepth;
    private Double perforationTop;
    private Double perforationBottom;
    private Double pumpSettingDepth;
    private Instant createdAt;
    private Instant updatedAt;
}
