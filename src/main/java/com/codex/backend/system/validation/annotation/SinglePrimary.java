package com.codex.backend.system.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.codex.backend.system.validation.validator.SinglePrimaryValidator;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SinglePrimaryValidator.class)
@Documented
public @interface SinglePrimary {

    String message() default "Exactly one entry must be marked as primary";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
