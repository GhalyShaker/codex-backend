package com.codex.backend.civilarchive.dto.response;

public record PhoneNumberResponse(
        Long phoneNumberId,
        String phoneNumber,
        boolean primary
) {
}
