package com.codex.backend.civilarchive.dto.request;

import java.time.LocalDate;
import org.openapitools.jackson.nullable.JsonNullable;

import com.codex.backend.reference.entity.enums.BloodType;
import com.codex.backend.reference.entity.enums.Gender;
import com.codex.backend.system.validation.annotation.ArabicName;
import com.codex.backend.system.validation.annotation.EnglishName;
import com.codex.backend.system.validation.annotation.JsonNullableNotNull;
import com.codex.backend.system.validation.annotation.NationalNumber;

import jakarta.validation.constraints.PastOrPresent;

public record PatchPersonRequest(
        @JsonNullableNotNull JsonNullable<@ArabicName String> firstNameArabic,
        @JsonNullableNotNull JsonNullable<@ArabicName String> middleNameArabic,
        @JsonNullableNotNull JsonNullable<@ArabicName String> lastNameArabic,

        @JsonNullableNotNull JsonNullable<@EnglishName String> firstNameEnglish,
        @JsonNullableNotNull JsonNullable<@EnglishName String> middleNameEnglish,
        @JsonNullableNotNull JsonNullable<@EnglishName String> lastNameEnglish,

        @JsonNullableNotNull JsonNullable<Gender> gender,
        JsonNullable<@PastOrPresent LocalDate> birthDate,
        JsonNullable<@NationalNumber String> nationalNumber,

        JsonNullable<Long> fatherId,
        JsonNullable<Long> motherId,

        JsonNullable<BloodType> bloodType) {
}
