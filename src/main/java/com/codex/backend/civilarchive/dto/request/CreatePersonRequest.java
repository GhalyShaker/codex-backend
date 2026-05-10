package com.codex.backend.civilarchive.dto.request;

import java.time.LocalDate;

import com.codex.backend.civilarchive.dto.PersonIdentifierCarrier;
import com.codex.backend.reference.entity.enums.BloodType;
import com.codex.backend.reference.entity.enums.Gender;
import com.codex.backend.system.validation.annotation.ArabicName;
import com.codex.backend.system.validation.annotation.EnglishName;
import com.codex.backend.system.validation.annotation.NationalNumber;
import com.codex.backend.system.validation.annotation.PhoneNumber;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record CreatePersonRequest(
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

        BloodType bloodType,

        @PhoneNumber String phoneNumber,
        @Email String emailAddress,
        @Valid AddressRequest address) implements PersonIdentifierCarrier {
}


//@Valid @SinglePrimary(message = "Exactly one address must be marked as primary") List<AddressRequest> addresses