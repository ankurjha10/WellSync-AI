package com.wellsync.ai.dto;

import com.wellsync.ai.entity.enums.CommandSource;
import com.wellsync.ai.entity.enums.CommandStatus;
import com.wellsync.ai.entity.enums.CommandType;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class ControlCommandResponse {
    private UUID id;
    private UUID wellId;
    private UUID recommendationId;
    private CommandType commandType;
    private Double previousValue;
    private Double requestedValue;
    private String unit;
    private CommandSource source;
    private CommandStatus status;
    private UUID requestedById;
    private String reason;
    private String failureReason;
    private Instant requestedAt;
    private Instant executedAt;
}
