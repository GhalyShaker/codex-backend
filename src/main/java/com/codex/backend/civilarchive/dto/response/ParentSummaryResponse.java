package com.codex.backend.civilarchive.dto.response;

public record ParentSummaryResponse(
        Long personId,

        String firstNameArabic,
        String middleNameArabic,
        String lastNameArabic,

        String firstNameEnglish,
        String middleNameEnglish,
        String lastNameEnglish
) {
}
