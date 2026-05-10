package com.codex.backend.system.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import com.codex.backend.system.validation.annotation.ArabicName;

public class ArabicNameValidator
                implements ConstraintValidator<ArabicName, String> {

        private static final Pattern ARABIC_PATTERN = Pattern.compile("^[\\p{IsArabic}]+$");

        @Override
        public boolean isValid(String value,
                        ConstraintValidatorContext context) {
                if (value == null) {
                        return true;
                }

                return value != null
                                && !value.isBlank()
                                && ARABIC_PATTERN.matcher(value).matches();
        }
}
