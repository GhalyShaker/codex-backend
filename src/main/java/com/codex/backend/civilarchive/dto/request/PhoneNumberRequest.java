package com.codex.backend.civilarchive.dto.request;

import com.codex.backend.system.validation.annotation.PhoneNumber;

public record PhoneNumberRequest(
        @PhoneNumber String phoneNumber,
        boolean primary
) {
}
