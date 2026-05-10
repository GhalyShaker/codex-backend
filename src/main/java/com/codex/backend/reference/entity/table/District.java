package com.codex.backend.reference.entity.table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "districts", schema = "reference")
@Getter
@NoArgsConstructor
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id")
    private Integer districtId;

    @Column(name = "district_name_english", nullable = false, length = 50, columnDefinition = "reference.english_location_name")
    private String districtNameEnglish;

    @Column(name = "district_name_arabic", nullable = false, length = 50, columnDefinition = "reference.arabic_location_name")
    private String districtNameArabic;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "governorate_id", nullable = false)
    private Governorate governorate;

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }
}
