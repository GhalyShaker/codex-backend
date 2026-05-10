package com.codex.backend.identity.dto;

public record UserLoginDTO(Integer userId, String email, String username, String passwordHash) {
}
