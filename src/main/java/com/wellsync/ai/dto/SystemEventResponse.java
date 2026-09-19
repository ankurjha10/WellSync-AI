package com.wellsync.ai.dto;

import com.wellsync.ai.entity.enums.SystemEventType;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class SystemEventResponse {
    private UUID id;
    private UUID wellId;
    private SystemEventType eventType;
    private String severity;
    private String message;
    private String metadata;
    private Instant createdAt;
}
