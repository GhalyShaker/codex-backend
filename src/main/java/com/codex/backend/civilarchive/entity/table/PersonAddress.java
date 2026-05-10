package com.codex.backend.civilarchive.entity.table;

import java.time.Instant;

import com.codex.backend.reference.entity.table.District;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "persons_addresses", schema = "civil_archive")
@Getter
@NoArgsConstructor
public class PersonAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @Column(name = "street", columnDefinition = "reference.arabic_street", nullable = false)
    private String street;

    @Column(name = "building_no", nullable = false)
    private String buildingNo;

    @Column(name = "floor_no", nullable = false)
    private String floorNo;

    @Column(name = "apartment_no", nullable = false)
    private String apartmentNo;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "address_line_english", columnDefinition = "reference.address_line_english")
    private String addressLineEnglish;

    @Column(name = "is_primary", nullable = false)
    private boolean primary;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public void setApartmentNo(String apartmentNo) {
        this.apartmentNo = apartmentNo;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setAddressLineEnglish(String addressLineEnglish) {
        this.addressLineEnglish = addressLineEnglish;
    }

    public void setPrimary(boolean isPrimary) {
        this.primary = isPrimary;
    }

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
}
