package com.codex.backend.civilarchive.entity.table;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.codex.backend.reference.entity.enums.BloodType;
import com.codex.backend.reference.entity.enums.Gender;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "persons", schema = "civil_archive")
@Getter
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long personId;

    @Column(name = "first_name_arabic", columnDefinition = "reference.arabic_name", length = 50, nullable = false)
    private String firstNameArabic;

    @Column(name = "middle_name_arabic", columnDefinition = "reference.arabic_name", length = 50, nullable = false)
    private String middleNameArabic;

    @Column(name = "last_name_arabic", columnDefinition = "reference.arabic_name", length = 50, nullable = false)
    private String lastNameArabic;

    @Column(name = "first_name_english", columnDefinition = "reference.english_name", length = 50, nullable = false)
    private String firstNameEnglish;

    @Column(name = "middle_name_english", columnDefinition = "reference.english_name", length = 50, nullable = false)
    private String middleNameEnglish;

    @Column(name = "last_name_english", columnDefinition = "reference.english_name", length = 50, nullable = false)
    private String lastNameEnglish;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "gender", columnDefinition = "reference.gender")
    private Gender gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "national_number", columnDefinition = "reference.national_number", length = 50)
    private String nationalNumber;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonPhoneNumber> phoneNumbers = new ArrayList<>();

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonEmailAddress> emailAddresses = new ArrayList<>();

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonAddress> addresses = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "father_id")
    private Person father;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mother_id")
    private Person mother;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "blood_type", columnDefinition = "reference.blood_type")
    private BloodType bloodType;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    // =========================
    // Controlled setters (IMPORTANT)
    // =========================

    public void updateNames(
            String firstNameArabic,
            String middleNameArabic,
            String lastNameArabic,
            String firstNameEnglish,
            String middleNameEnglish,
            String lastNameEnglish) {
        this.firstNameArabic = firstNameArabic;
        this.middleNameArabic = middleNameArabic;
        this.lastNameArabic = lastNameArabic;
        this.firstNameEnglish = firstNameEnglish;
        this.middleNameEnglish = middleNameEnglish;
        this.lastNameEnglish = lastNameEnglish;
    }

    public void setFirstNameArabic(String firstNameArabic) {
        this.firstNameArabic = firstNameArabic;
    }

    public void setMiddleNameArabic(String middleNameArabic) {
        this.middleNameArabic = middleNameArabic;
    }

    public void setLastNameArabic(String lastNameArabic) {
        this.lastNameArabic = lastNameArabic;
    }

    public void setFirstNameEnglish(String firstNameEnglish) {
        this.firstNameEnglish = firstNameEnglish;
    }

    public void setMiddleNameEnglish(String middleNameEnglish) {
        this.middleNameEnglish = middleNameEnglish;
    }

    public void setLastNameEnglish(String lastNameEnglish) {
        this.lastNameEnglish = lastNameEnglish;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setNationalNumber(String nationalNumber) {
        this.nationalNumber = nationalNumber;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public void setFather(Person father) {
        this.father = father;
    }

    public void setMother(Person mother) {
        this.mother = mother;
    }

    // PHONE
    public void addPhone(PersonPhoneNumber phone) {
        phoneNumbers.add(phone);
        phone.setPerson(this);
    }

    public void removePhone(PersonPhoneNumber phone) {
        phoneNumbers.remove(phone);
        phone.setPerson(null);
    }

    // EMAIL
    public void addEmail(PersonEmailAddress email) {
        emailAddresses.add(email);
        email.setPerson(this);
    }

    public void removeEmail(PersonEmailAddress email) {
        emailAddresses.remove(email);
        email.setPerson(null);
    }

    // ADDRESS
    public void addAddress(PersonAddress address) {
        addresses.add(address);
        address.setPerson(this);
    }

    public void removeAddress(PersonAddress address) {
        addresses.remove(address);
        address.setPerson(null);
    }
}