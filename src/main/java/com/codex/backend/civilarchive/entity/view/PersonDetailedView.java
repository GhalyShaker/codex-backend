package com.codex.backend.civilarchive.entity.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.codex.backend.reference.entity.enums.BloodType;
import com.codex.backend.reference.entity.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "person_detailed_view", schema = "civil_archive")
public class PersonDetailedView {

    @Id
    @Column(name = "person_id")
    private Long personId;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "national_number")
    private String nationalNumber;

    @Column(name = "first_name_arabic")
    private String firstNameArabic;

    @Column(name = "father_name_arabic")
    private String fatherNameArabic;

    @Column(name = "grandfather_name_arabic")
    private String grandfatherNameArabic;

    @Column(name = "great_grandfather_name_arabic")
    private String greatGrandfatherNameArabic;

    @Column(name = "first_name_english")
    private String firstNameEnglish;

    @Column(name = "father_name_english")
    private String fatherNameEnglish;

    @Column(name = "grandfather_name_english")
    private String grandfatherNameEnglish;

    @Column(name = "great_grandfather_name_english")
    private String greatGrandfatherNameEnglish;

    @Column(name = "full_name_arabic")
    private String fullNameArabic;

    @Column(name = "full_name_english")
    private String fullNameEnglish;

    @Column(name = "father_id")
    private Long fatherId;

    @Column(name = "father_full_name_arabic")
    private String fatherFullNameArabic;

    @Column(name = "father_full_name_english")
    private String fatherFullNameEnglish;

    @Column(name = "father_primary_phone_number")
    private String fatherPrimaryPhoneNumber;

    @Column(name = "mother_id")
    private Long motherId;

    // NEW FIELDS

    @Column(name = "mother_first_name_arabic")
    private String motherFirstNameArabic;

    @Column(name = "mother_father_name_arabic")
    private String motherFatherNameArabic;

    @Column(name = "mother_grandfather_name_arabic")
    private String motherGrandfatherNameArabic;

    @Column(name = "mother_first_name_english")
    private String motherFirstNameEnglish;

    @Column(name = "mother_father_name_english")
    private String motherFatherNameEnglish;

    @Column(name = "mother_grandfather_name_english")
    private String motherGrandfatherNameEnglish;

    @Column(name = "mother_full_name_arabic")
    private String motherFullNameArabic;

    @Column(name = "mother_full_name_english")
    private String motherFullNameEnglish;

    @Column(name = "mother_primary_phone_number")
    private String motherPrimaryPhoneNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    
    @Column(name = "blood_type")
    private BloodType bloodType;

    @Column(name = "person_primary_phone_number")
    private String personPrimaryPhoneNumber;

    @Column(name = "person_primary_email_address")
    private String personPrimaryEmailAddress;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
