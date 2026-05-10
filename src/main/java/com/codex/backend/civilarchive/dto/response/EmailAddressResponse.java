package com.codex.backend.civilarchive.dto.response;

public record EmailAddressResponse(
        Long emailAddressId,
        String emailAddress,
        boolean primary
) {
}
