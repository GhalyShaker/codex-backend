package com.codex.backend.civilarchive.dto.request;

import java.time.LocalDate;

import com.codex.backend.reference.entity.enums.BloodType;
import com.codex.backend.reference.entity.enums.Gender;
import com.codex.backend.system.validation.annotation.ArabicName;
import com.codex.backend.system.validation.annotation.EnglishName;
import com.codex.backend.system.validation.annotation.NationalNumber;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record PutPersonRequest(
                @ArabicName @NotBlank String firstNameArabic,
                @ArabicName @NotBlank String middleNameArabic,
                @ArabicName @NotBlank String lastNameArabic,

                @EnglishName @NotBlank String firstNameEnglish,
                @EnglishName @NotBlank String middleNameEnglish,
                @EnglishName @NotBlank String lastNameEnglish,

                @NotNull Gender gender,
                @PastOrPresent LocalDate birthDate,
                @NationalNumber String nationalNumber,

                Long fatherId,
                Long motherId,

                BloodType bloodType) {
}
