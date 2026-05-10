package com.codex.backend.system.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.codex.backend.system.validation.validator.JsonNullableNotNullValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = JsonNullableNotNullValidator.class)
public @interface JsonNullableNotNull {
    String message() default "Field cannot be explicitly set to null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
