package com.codex.backend.system.validation.validator;

import com.codex.backend.system.validation.annotation.AddressLineEnglish;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AddressLineEnglishValidator implements ConstraintValidator<AddressLineEnglish, String> {

    private static final String REGEX = ".*[A-Za-z].*"; // must contain at least one Latin letter

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return false; // not null
        String trimmed = value.trim();
        if (trimmed.isEmpty())
            return false; // not empty
        return trimmed.matches(REGEX); // contains at least one Latin letter
    }
}
