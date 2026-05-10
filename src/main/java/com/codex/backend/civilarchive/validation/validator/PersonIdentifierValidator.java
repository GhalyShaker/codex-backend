package com.codex.backend.civilarchive.validation.validator;

import com.codex.backend.civilarchive.dto.PersonIdentifierCarrier;
import com.codex.backend.civilarchive.validation.annotation.ValidPersonIdentifier;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PersonIdentifierValidator implements ConstraintValidator<ValidPersonIdentifier, PersonIdentifierCarrier> {

@Override
public boolean isValid(PersonIdentifierCarrier request,
                       ConstraintValidatorContext context) {

    if (request == null) {
        return true;
    }

    boolean hasNationalNumber =
            request.nationalNumber() != null
            && !request.nationalNumber().isBlank();

    boolean hasPhoneNumber =
            request.phoneNumber() != null
            && !request.phoneNumber().isBlank();

    boolean hasParents =
            request.fatherId() != null
            || request.motherId() != null;

    boolean valid =
            hasNationalNumber
            || hasPhoneNumber
            || hasParents;

    if (!valid) {

        context.disableDefaultConstraintViolation();

        context.buildConstraintViolationWithTemplate(
                "Provide national number or phone number or one of the parents"
        ).addConstraintViolation();
    }

    return valid;
}
}
