package com.wellsync.ai.dto;

import com.wellsync.ai.entity.enums.WellStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class WellRequest {
    @NotBlank(message = "Well code is required")
    private String wellCode;

    private String wellName;

    @NotBlank(message = "Field name is required")
    private String fieldName;

    private String location;

    @NotNull(message = "Reservoir ID is required")
    private UUID reservoirId;

    @NotNull(message = "Status is required")
    private WellStatus status;
}
