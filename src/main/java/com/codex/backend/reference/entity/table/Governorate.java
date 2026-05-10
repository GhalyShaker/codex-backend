package com.codex.backend.reference.entity.table;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "governorates", schema = "reference")
@Getter
@NoArgsConstructor
public class Governorate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "governorate_id")
    private Integer governorateId;

    @OneToMany(mappedBy = "governorate", fetch = FetchType.LAZY)
    private List<District> districts = new ArrayList<>();

    @Column(name = "governorate_name_english", nullable = false, length = 50, columnDefinition = "reference.arabic_location_name")
    private String governorateNameEnglish;

    @Column(name = "governorate_name_arabic", nullable = false, length = 50, columnDefinition = "reference.english_location_name")
    private String governorateNameArabic;
}
