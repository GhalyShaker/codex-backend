package com.codex.backend.reference.entity.converter;

import com.codex.backend.reference.entity.enums.Gender;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, String> {

    @Override
    public String convertToDatabaseColumn(Gender gender) {
        if (gender == null)
            return null;

        return switch (gender) {
            case MALE -> "MALE";
            case FEMALE -> "FEMALE";
        };
    }

    @Override
    public Gender convertToEntityAttribute(String dbValue) {
        if (dbValue == null)
            return null;

        return switch (dbValue.toUpperCase()) {
            case "MALE" -> Gender.MALE;
            case "FEMALE" -> Gender.FEMALE;
            default -> throw new IllegalArgumentException("Unknown gender value: " + dbValue);
        };
    }
}
