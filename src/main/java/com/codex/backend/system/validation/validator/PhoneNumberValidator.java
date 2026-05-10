package com.codex.backend.system.validation.validator;

import com.codex.backend.system.validation.annotation.PhoneNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator
        implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public boolean isValid(String value,
            ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return !value.isBlank()
                && value.matches("^\\d+$");
    }
}
