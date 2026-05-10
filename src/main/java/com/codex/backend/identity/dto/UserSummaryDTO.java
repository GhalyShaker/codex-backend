package com.codex.backend.identity.dto;

import java.time.LocalDateTime;

public record UserSummaryDTO(Integer id, String username, String email, Boolean isActive, LocalDateTime createdAt,
                LocalDateTime updatedAt) {
}
