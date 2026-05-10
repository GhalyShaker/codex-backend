package com.codex.backend.civilarchive.rules;

import org.springframework.stereotype.Component;

import com.codex.backend.civilarchive.entity.table.Person;
import com.codex.backend.civilarchive.mapper.CivilArchiveErrorCode;
import com.codex.backend.civilarchive.repository.table.PersonRepository;
import com.codex.backend.reference.entity.enums.Gender;
import com.codex.backend.system.exception.BusinessException;
import com.codex.backend.system.exception.ConflictException;
import com.codex.backend.system.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PersonRules {

    private final PersonRepository personRepository;

    public Person getPerson(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> personNotFound(id));
    }

    public Person getMalePerson(Long id, String message) {
        message = (message == null) ? "Person must be male." : message;
        Person person = getPerson(id);
        if (person.getGender() != Gender.MALE) {
            throw new BusinessException(CivilArchiveErrorCode.PERSON_GENDER_ROLE_MISMATCH, message);
        }

        return person;
    }

    public Person getFemalePerson(Long id, String message) {
        message = (message == null) ? "Person must be female." : message;
        Person person = getPerson(id);

        if (person.getGender() != Gender.FEMALE) {
            throw new BusinessException(CivilArchiveErrorCode.PERSON_GENDER_ROLE_MISMATCH, message);
        }

        return person;
    }

    public void checkNationalNumberUnique(String nationalNumber) {
        if (nationalNumber == null) {
            return;
        }
        if (personRepository.existsByNationalNumber(nationalNumber)) {
            throw new ConflictException(CivilArchiveErrorCode.PERSON_NATIONAL_ID_EXISTS,
                    "National number already exists");
        }
    }

    public void validateIdentifiable(Person person) {

        if (person.getPhoneNumbers().isEmpty()
                && person.getMother() == null
                && person.getFather() == null
                && person.getNationalNumber() == null) {

            throw new BusinessException(
                    CivilArchiveErrorCode.PERSON_INSUFFICIENT_IDENTIFICATION,
                    "Person must have at least a father, mother, national number, or phone number.");
        }
    }

    private NotFoundException personNotFound(Long id) {
        return new NotFoundException("Person", id);
    }
}