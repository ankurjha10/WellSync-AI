package com.wellsync.ai.dto;

import com.wellsync.ai.entity.enums.CssCycleStatus;
import com.wellsync.ai.entity.enums.CssStage;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class CssCycleResponse {
    private UUID id;
    private UUID wellId;
    private Integer cycleNumber;
    private CssStage stage;
    private CssCycleStatus status;
    private Integer soakTimeMinutes;
    private Instant startTime;
    private Instant endTime;
    private String notes;
    private Instant createdAt;
}
