package com.codex.backend.civilarchive.dto.request;

import com.codex.backend.system.validation.annotation.AddressLineEnglish;
import com.codex.backend.system.validation.annotation.ArabicStreet;

public record AddressRequest(
        Integer districtId,
        @ArabicStreet String street,
        String buildingNo,
        String floorNo,
        String apartmentNo,

        Double latitude,
        Double longitude,

        String postalCode,

        @AddressLineEnglish String addressLineEnglish,
        boolean primary
) {
}
