package com.wellsync.ai.dto;

import com.wellsync.ai.entity.enums.WellStatus;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class WellResponse {
    private UUID id;
    private String wellCode;
    private String wellName;
    private String fieldName;
    private String location;
    private UUID reservoirId;
    private WellStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}
