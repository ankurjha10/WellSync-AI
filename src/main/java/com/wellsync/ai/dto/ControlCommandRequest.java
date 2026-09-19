package com.wellsync.ai.dto;

import com.wellsync.ai.entity.enums.CommandSource;
import com.wellsync.ai.entity.enums.CommandStatus;
import com.wellsync.ai.entity.enums.CommandType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ControlCommandRequest {
    @NotNull(message = "Well ID is required")
    private UUID wellId;

    private UUID recommendationId;

    @NotNull(message = "Command type is required")
    private CommandType commandType;

    private Double previousValue;
    private Double requestedValue;
    private String unit;

    @NotNull(message = "Source is required")
    private CommandSource source;

    @NotNull(message = "Status is required")
    private CommandStatus status;

    private UUID requestedById;
    private String reason;
}
