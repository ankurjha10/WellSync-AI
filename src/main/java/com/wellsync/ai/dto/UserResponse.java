package com.wellsync.ai.dto;

import com.wellsync.ai.entity.enums.UserRole;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class UserResponse {
    private UUID id;
    private String name;
    private String email;
    private UserRole role;
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
}
