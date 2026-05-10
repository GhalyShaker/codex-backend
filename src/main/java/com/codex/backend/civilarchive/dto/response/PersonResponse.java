package com.codex.backend.civilarchive.dto.response;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import com.codex.backend.reference.entity.enums.BloodType;
import com.codex.backend.reference.entity.enums.Gender;

public record PersonResponse(
                Long personId,

                String firstNameArabic,
                String middleNameArabic,
                String lastNameArabic,

                String firstNameEnglish,
                String middleNameEnglish,
                String lastNameEnglish,

                List<AddressResponse> addresses,

                ContactInformationsResponse contacts,

                ParentSummaryResponse father,
                ParentSummaryResponse mother,

                Gender gender,
                LocalDate birthDate,
                String nationalNumber,
                BloodType bloodType,

                Instant createdAt,
                Instant updatedAt) {
}
