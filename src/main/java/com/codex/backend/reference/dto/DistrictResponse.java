package com.codex.backend.reference.dto;

public record DistrictResponse(
        Integer districtId,
        String districtNameArabic,
        String districtNameEnglish,
        Integer governorateId
) {}
