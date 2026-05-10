package com.codex.backend.civilarchive.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.codex.backend.civilarchive.validation.validator.PersonIdentifierValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PersonIdentifierValidator.class)
public @interface ValidPersonIdentifier {
    String message() default "Provide national number or phone number or one of the parents";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
