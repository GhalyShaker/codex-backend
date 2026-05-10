package com.codex.backend.civilarchive.mapper;

import org.springframework.stereotype.Component;

import com.codex.backend.system.error.SystemErrorCode;
import com.codex.backend.system.exception.AppException;
import com.codex.backend.system.exception.BusinessException;
import com.codex.backend.system.exception.ConflictException;
import com.codex.backend.system.mapper.ConstraintMapper;

@Component
public class CivilRegistryConstraintMapper
        implements ConstraintMapper {

    @Override
    public boolean supports(String constraint) {

        if (constraint == null) {
            return false;
        }

        return constraint.startsWith("uk_person_")
                || constraint.startsWith("fk_person_")
                || constraint.startsWith("chk_person_");
    }

    @Override
    public AppException map(String constraint) {

        return switch (constraint) {

            case "uk_person_national_id" ->
                new ConflictException(
                        CivilArchiveErrorCode.PERSON_NATIONAL_ID_EXISTS,
                        "National ID already exists"
                );

            case "fk_person_region" ->
                new BusinessException(
                        CivilArchiveErrorCode.PERSON_INVALID_REGION,
                        "Region does not exist"
                );

            case "chk_person_birth_date" ->
                new BusinessException(
                        CivilArchiveErrorCode.PERSON_INVALID_BIRTH_DATE,
                        "Birth date is invalid"
                );

            default ->
                new BusinessException(
                        SystemErrorCode.DATA_INTEGRITY_ERROR,
                        "Civil registry data integrity violation"
                );
        };
    }
}
