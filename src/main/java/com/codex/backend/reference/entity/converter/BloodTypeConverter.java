package com.codex.backend.reference.entity.converter;

import com.codex.backend.reference.entity.enums.BloodType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BloodTypeConverter implements AttributeConverter<BloodType, String> {

    @Override
    public String convertToDatabaseColumn(BloodType attribute) {
        if (attribute == null)
            return null;
        return attribute.name(); // A_POS, O_NEG, etc.
    }

    @Override
    public BloodType convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;
        return BloodType.valueOf(dbData);
    }
}
