package com.codex.backend.civilarchive.dto.filter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.codex.backend.reference.entity.enums.BloodType;
import com.codex.backend.reference.entity.enums.Gender;

import lombok.Data;

@Data
public class PersonFilter {

    private Long personId;
    private String nationalNumber;

    private Gender gender;

    // Person names
    private String firstNameArabic;
    private String fatherNameArabic;
    private String grandfatherNameArabic;
    private String greatGrandfatherNameArabic;

    private String firstNameEnglish;
    private String fatherNameEnglish;
    private String grandfatherNameEnglish;
    private String greatGrandfatherNameEnglish;

    // Mother names
    private String motherFirstNameArabic;
    private String motherFatherNameArabic;
    private String motherGrandfatherNameArabic;

    private String motherFirstNameEnglish;
    private String motherFatherNameEnglish;
    private String motherGrandfatherNameEnglish;

    // Relationships
    private Long fatherId;
    private Long motherId;

    // Contact information
    private String fatherPrimaryPhoneNumber;
    private String motherPrimaryPhoneNumber;
    private String personPrimaryPhoneNumber;
    private String personPrimaryEmailAddress;

    // Other
    private BloodType bloodType;

    private LocalDate birthDateFrom;
    private LocalDate birthDateTo;

    private LocalDateTime createdAtFrom;
    private LocalDateTime createdAtTo;

    private LocalDateTime updatedAtFrom;
    private LocalDateTime updatedAtTo;
}
