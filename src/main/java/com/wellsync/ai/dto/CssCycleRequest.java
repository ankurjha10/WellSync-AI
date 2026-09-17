package com.wellsync.ai.dto;

import com.wellsync.ai.entity.enums.CssCycleStatus;
import com.wellsync.ai.entity.enums.CssStage;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class CssCycleRequest {
    @NotNull(message = "Well ID is required")
    private UUID wellId;

    @NotNull(message = "Cycle number is required")
    private Integer cycleNumber;

    private CssStage stage;
    private CssCycleStatus status;
    private Integer soakTimeMinutes;
    private Instant startTime;
    private Instant endTime;
    private String notes;
}
