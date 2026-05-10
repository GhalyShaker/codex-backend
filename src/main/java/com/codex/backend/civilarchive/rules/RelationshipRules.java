package com.codex.backend.civilarchive.rules;

import org.springframework.stereotype.Component;

import com.codex.backend.civilarchive.entity.table.Person;
import com.codex.backend.civilarchive.mapper.CivilArchiveErrorCode;
import com.codex.backend.reference.entity.enums.Gender;
import com.codex.backend.system.exception.BusinessException;

@Component
public class RelationshipRules {

    public void validateFather(Person person) {

        if (person.getGender() != Gender.MALE) {
            throw new BusinessException(
                    CivilArchiveErrorCode.PERSON_GENDER_ROLE_MISMATCH,
                    "Father must be male."
            );
        }
    }

    public void validateMother(Person person) {

        if (person.getGender() != Gender.FEMALE) {
            throw new BusinessException(
                    CivilArchiveErrorCode.PERSON_GENDER_ROLE_MISMATCH,
                    "Mother must be female."
            );
        }
    }
}