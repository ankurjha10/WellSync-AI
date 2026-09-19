package com.wellsync.ai.dto;

import com.wellsync.ai.entity.enums.SystemEventType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class SystemEventRequest {
    private UUID wellId;

    @NotNull(message = "Event type is required")
    private SystemEventType eventType;

    private String severity;
    private String message;
    private String metadata;
}
