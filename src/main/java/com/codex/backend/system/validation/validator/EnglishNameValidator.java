package com.codex.backend.system.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import com.codex.backend.system.validation.annotation.EnglishName;

public class EnglishNameValidator
                implements ConstraintValidator<EnglishName, String> {

        private static final Pattern ENGLISH_PATTERN = Pattern.compile("^[A-Za-z]+$");

        @Override
        public boolean isValid(String value,
                        ConstraintValidatorContext context) {
                if (value == null) {
                        return true;
                }

                return value != null
                                && !value.isBlank()
                                && ENGLISH_PATTERN.matcher(value).matches();
        }
}
