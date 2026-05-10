package com.codex.backend.system.validation.validator;

import com.codex.backend.system.validation.annotation.NationalNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NationalNumberValidator
        implements ConstraintValidator<NationalNumber, String> {

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
