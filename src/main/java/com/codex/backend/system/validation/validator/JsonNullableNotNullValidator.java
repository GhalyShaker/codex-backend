package com.codex.backend.system.validation.validator;

import org.openapitools.jackson.nullable.JsonNullable;

import com.codex.backend.system.validation.annotation.JsonNullableNotNull;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class JsonNullableNotNullValidator
        implements ConstraintValidator<JsonNullableNotNull, JsonNullable<?>> {

    @Override
    public boolean isValid(
            JsonNullable<?> value,
            ConstraintValidatorContext context) {

        if (value == null || !value.isPresent()) {
            return true;
        }

        return value.get() != null;
    }
}
