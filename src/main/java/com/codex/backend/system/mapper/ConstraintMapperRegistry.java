package com.codex.backend.system.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.codex.backend.system.error.SystemErrorCode;
import com.codex.backend.system.exception.AppException;
import com.codex.backend.system.exception.BusinessException;

@Component
public class ConstraintMapperRegistry {

    private final List<ConstraintMapper> mappers;

    public ConstraintMapperRegistry(List<ConstraintMapper> mappers) {
        this.mappers = mappers;
    }

    public AppException map(String constraint) {

        if (constraint == null) {
            return new BusinessException(
                SystemErrorCode.DATA_INTEGRITY_ERROR,
                "Database constraint violation"
            );
        }

        return mappers.stream()
            .filter(m -> m.supports(constraint))
            .findFirst()
            .map(m -> m.map(constraint))
            .orElse(
                new BusinessException(
                    SystemErrorCode.DATA_INTEGRITY_ERROR,
                    "Unknown constraint violation: " + constraint
                )
            );
    }
}
