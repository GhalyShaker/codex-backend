package com.codex.backend.system.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.codex.backend.system.validation.validator.ArabicNameValidator;

@Documented
@Constraint(validatedBy = ArabicNameValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ArabicName {

    String message() default "Must contain Arabic letters only";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}