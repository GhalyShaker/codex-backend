package com.codex.backend.auth.dto;

import java.time.Instant;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        String email,
        String username,
        Long userId,
        Instant expiresAt
) {
}