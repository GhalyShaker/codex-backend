package com.codex.backend.civilarchive.dto.response;

import com.codex.backend.reference.dto.DistrictResponse;

public record AddressResponse(
        Long addressId,

        DistrictResponse district,

        String street,
        String buildingNo,
        String floorNo,
        String apartmentNo,

        Double latitude,
        Double longitude,

        String postalCode,
        String addressLineEnglish,

        boolean primary
) {}
