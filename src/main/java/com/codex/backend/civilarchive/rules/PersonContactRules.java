package com.codex.backend.civilarchive.rules;

import org.springframework.stereotype.Component;

import com.codex.backend.civilarchive.entity.table.PersonEmailAddress;
import com.codex.backend.civilarchive.entity.table.PersonPhoneNumber;
import com.codex.backend.civilarchive.mapper.CivilArchiveErrorCode;
import com.codex.backend.civilarchive.repository.table.PersonEmailAddressRepository;
import com.codex.backend.civilarchive.repository.table.PersonPhoneNumberRepository;
import com.codex.backend.system.exception.ConflictException;
import com.codex.backend.system.exception.NotFoundException;

@Component
public class PersonContactRules {

    private final PersonPhoneNumberRepository personPhoneNumberRepository;
    private final PersonPhoneNumberRepository phoneNumberRepository;
    private final PersonEmailAddressRepository emailAddressRepository;

    public PersonContactRules(
            PersonPhoneNumberRepository phoneNumberRepository,
            PersonEmailAddressRepository emailAddressRepository,
            PersonPhoneNumberRepository personPhoneNumberRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.emailAddressRepository = emailAddressRepository;
        this.personPhoneNumberRepository = personPhoneNumberRepository;
    }

    public void checkPhoneUnique(String phone) {
        if (phone == null) {
            return;
        }
        if (phoneNumberRepository.existsByPhoneNumber(phone)) {
            throw new ConflictException(
                    CivilArchiveErrorCode.PERSON_PHONE_NUMBER_EXISTS,
                    "Phone already exists: " + phone);
        }
    }

    public void checkEmailUnique(String email) {
        if (email == null) {
            return;
        }
        if (emailAddressRepository.existsByEmailAddress(email)) {
            throw new ConflictException(
                    CivilArchiveErrorCode.PERSON_EMAIL_ADDRESS_EXISTS,
                    "Email already exists: " + email);
        }
    }

    public PersonEmailAddress getEmail(Long emailId) {
        return emailAddressRepository.findById(emailId)
                .orElseThrow(() -> new NotFoundException("Email", emailId));
    }

    public PersonPhoneNumber getPhone(Long phoneId) {
        return phoneNumberRepository.findById(phoneId)
                .orElseThrow(() -> new NotFoundException("Phone", phoneId));
    }

    public PersonPhoneNumber getPhoneOfPerson(Long phoneId, Long personId) {
        if (phoneId == null) {
            return null;
        }
        return personPhoneNumberRepository.findByPhoneNumberIdAndPerson_PersonId(phoneId, personId)
                .orElseThrow(() -> new NotFoundException(
                        "Phone", phoneId));
    }

    public PersonEmailAddress getEmailOfPerson(Long personId, Long emailId) {
        if (emailId == null) {
            return null;
        }
        return emailAddressRepository.findByEmailAddressIdAndPerson_PersonId(emailId, personId)
                .orElseThrow(() -> new NotFoundException(
                        "Email", emailId));
    }
}