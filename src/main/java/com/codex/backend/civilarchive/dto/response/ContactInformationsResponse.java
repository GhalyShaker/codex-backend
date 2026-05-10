package com.codex.backend.civilarchive.dto.response;

import java.util.List;

public record ContactInformationsResponse(
    List<PhoneNumberResponse> phoneNumbers,
    List<EmailAddressResponse> emailAddresses
) {
}
