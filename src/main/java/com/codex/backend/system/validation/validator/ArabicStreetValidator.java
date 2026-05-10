package com.codex.backend.system.validation.validator;

import com.codex.backend.system.validation.annotation.ArabicStreet;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ArabicStreetValidator implements ConstraintValidator<ArabicStreet, String> {

    // Regex from your PostgreSQL domain
    private static final String ARABIC_STREET_REGEX = "^[ء-ي٠-٩0-9 ,\\-]+$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // NULL is allowed
        }

        String trimmed = value.trim();

        // Check if only whitespace
        if (trimmed.isEmpty()) {
            return false;
        }

        // Check regex
        return trimmed.matches(ARABIC_STREET_REGEX);
    }
}
