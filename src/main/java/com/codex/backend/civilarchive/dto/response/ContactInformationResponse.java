package com.codex.backend.civilarchive.dto.response;

public record ContactInformationResponse(
        EmailAddressResponse email,
        PhoneNumberResponse phoneNumber) {
}
