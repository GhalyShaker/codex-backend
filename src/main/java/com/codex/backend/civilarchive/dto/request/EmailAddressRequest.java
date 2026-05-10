package com.codex.backend.civilarchive.dto.request;

import jakarta.validation.constraints.Email;

public record EmailAddressRequest(
        @Email String emailAddress,
        boolean primary
) {
}
