package com.codex.backend.system.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Method;
import java.util.List;

import com.codex.backend.system.validation.annotation.SinglePrimary;

public class SinglePrimaryValidator
        implements ConstraintValidator<SinglePrimary, List<?>> {

    @Override
    public boolean isValid(List<?> value,
                           ConstraintValidatorContext context) {

        if (value == null || value.isEmpty()) {
            return true; // use @NotEmpty if required
        }

        long primaryCount = value.stream()
                .filter(this::isPrimary)
                .count();

        return primaryCount == 1;
    }

    private boolean isPrimary(Object obj) {
        try {
            Method method = obj.getClass().getMethod("primary");
            return (boolean) method.invoke(obj);
        } catch (Exception e) {
            return false;
        }
    }
}
