package com.codex.backend.civilarchive.service.table;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codex.backend.civilarchive.dto.filter.PersonFilter;
import com.codex.backend.civilarchive.dto.request.AddressRequest;
import com.codex.backend.civilarchive.dto.request.CreatePersonRequest;
import com.codex.backend.civilarchive.dto.request.EmailAddressRequest;
import com.codex.backend.civilarchive.dto.request.PatchPersonRequest;
import com.codex.backend.civilarchive.dto.request.PhoneNumberRequest;
import com.codex.backend.civilarchive.dto.request.PutPersonRequest;
import com.codex.backend.civilarchive.dto.response.AddressResponse;
import com.codex.backend.civilarchive.dto.response.EmailAddressResponse;
import com.codex.backend.civilarchive.dto.response.PersonResponse;
import com.codex.backend.civilarchive.dto.response.PhoneNumberResponse;
import com.codex.backend.civilarchive.entity.table.Person;
import com.codex.backend.civilarchive.entity.table.PersonAddress;
import com.codex.backend.civilarchive.entity.table.PersonEmailAddress;
import com.codex.backend.civilarchive.entity.table.PersonPhoneNumber;
import com.codex.backend.civilarchive.entity.view.PersonDetailedView;
import com.codex.backend.civilarchive.mapper.table.AddressMapper;
import com.codex.backend.civilarchive.mapper.table.EmailMapper;
import com.codex.backend.civilarchive.mapper.table.ParentMapper;
import com.codex.backend.civilarchive.mapper.table.PersonMapper;
import com.codex.backend.civilarchive.mapper.table.PhoneMapper;
import com.codex.backend.civilarchive.repository.table.PersonAddressRepository;
import com.codex.backend.civilarchive.repository.table.PersonEmailAddressRepository;
import com.codex.backend.civilarchive.repository.table.PersonPhoneNumberRepository;
import com.codex.backend.civilarchive.repository.table.PersonRepository;
import com.codex.backend.civilarchive.repository.view.PersonDetailedViewRepository;
import com.codex.backend.civilarchive.rules.AddressRules;
import com.codex.backend.civilarchive.rules.PersonContactRules;
import com.codex.backend.civilarchive.rules.PersonRules;
import com.codex.backend.civilarchive.rules.RelationshipRules;
import com.codex.backend.civilarchive.specification.view.PersonDetailedViewSpecification;
import com.codex.backend.reference.entity.table.District;
import com.codex.backend.reference.repository.DistrictRepository;
import com.codex.backend.reference.repository.GovernorateRepository;
import com.codex.backend.system.exception.NotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {

    @PersistenceContext
    private EntityManager entityManager;

    private final PersonRepository personRepository;
    private final PersonDetailedViewRepository personDetailedViewRepository;
    private final PersonPhoneNumberRepository personPhoneNumberRepository;
    private final PersonEmailAddressRepository personEmailAddressRepository;
    private final PersonAddressRepository personAddressRepository;
    private final GovernorateRepository governorateRepository;
    private final DistrictRepository districtRepository;
    private final PersonRules personRules;
    private final PersonContactRules personContactRules;
    private final RelationshipRules relationshipRules;
    private final AddressRules addressRules;
    private final PersonMapper personMapper;
    private final PhoneMapper phoneMapper;
    private final ParentMapper parentMapper;
    private final EmailMapper emailMapper;
    private final AddressMapper addressMapper;

    @Transactional
    public Long createPerson(CreatePersonRequest request) {
        Person person = personMapper.toEntity(request);

        if (request.nationalNumber() != null) {
            personRules.checkNationalNumberUnique(request.nationalNumber());
            person.setNationalNumber(request.nationalNumber());
        }

        if (request.fatherId() != null) {
            Person father = personRules.getMalePerson(request.fatherId(), "Father must be male.");
            person.setFather(father);
        }

        if (request.motherId() != null) {
            Person mother = personRules.getFemalePerson(request.motherId(), "Mother must be female.");
            person.setMother(mother);
        }

        if (request.phoneNumber() != null) {
            personContactRules.checkPhoneUnique(request.phoneNumber());
            PersonPhoneNumber personPhoneNumber = new PersonPhoneNumber();
            personPhoneNumber.setPhoneNumber(request.phoneNumber());
            personPhoneNumber.setPrimary(true);
            person.addPhone(personPhoneNumber);
        }

        if (request.emailAddress() != null) {
            personContactRules.checkEmailUnique(request.emailAddress());
            PersonEmailAddress personEmailAddress = new PersonEmailAddress();
            personEmailAddress.setEmailAddress(request.emailAddress());
            personEmailAddress.setPrimary(true);
            person.addEmail(personEmailAddress);
        }

        if (request.address() != null) {
            District district = addressRules.getDistrict(request.address().districtId());
            PersonAddress personAddress = addressMapper.toEntity(request.address());
            personAddress.setDistrict(district);
            personAddress.setPrimary(true);
            person.addAddress(personAddress);
        }

        return personRepository.save(person).getPersonId();
    }

    @Transactional(readOnly = true)
    public PersonResponse getPersonDetails(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new NotFoundException("Person", id));
        PersonResponse personResponse = personMapper.toResponse(person);
        return personResponse;
    }

    @Transactional(readOnly = true)
    public PersonDetailedView getPersonDetailed(Long id) {
        return personDetailedViewRepository.findById(id).orElseThrow(() -> new NotFoundException("Person", id));
    }

    @Transactional(readOnly = true)
    public Page<PersonDetailedView> searchPersonsDetailed(PersonFilter personFilter, Pageable pageable) {
        Specification<PersonDetailedView> specification = PersonDetailedViewSpecification.filter(personFilter);
        return personDetailedViewRepository.findAll(specification, pageable);
    }

    @Transactional
    public PersonDetailedView updatePerson(Long id, PutPersonRequest request) {

        Person person = personRules.getPerson(id);

        // validations
        if (request.nationalNumber() != null &&
                !request.nationalNumber().equals(person.getNationalNumber())) {
            personRules.checkNationalNumberUnique(request.nationalNumber());
        }

        person.setFather(
                request.fatherId() == null
                        ? null
                        : personRules.getMalePerson(request.fatherId(), "Father must be male."));
        person.setMother(
                request.motherId() == null
                        ? null
                        : personRules.getFemalePerson(request.motherId(), "Mother must be female."));

        // apply changes
        personMapper.updateFromPut(request, person);

        // FORCE DB sync BEFORE view query
        personRepository.flush();

        return personDetailedViewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person", id));
    }

    @Transactional
    public PersonDetailedView patchPerson(Long id, PatchPersonRequest request) {
        Person person = personRepository.findById(id).orElseThrow(() -> new NotFoundException("Person", id));
        personMapper.updateFromPatch(request, person);

        // validations
        if (request.nationalNumber().isPresent()) {

            String nationalNumber = request.nationalNumber().get();

            if (!Objects.equals(person.getNationalNumber(), nationalNumber)
                    && nationalNumber != null
                    && !nationalNumber.isBlank()) {

                personRules.checkNationalNumberUnique(nationalNumber);
            }

            person.setNationalNumber(nationalNumber);
        }

        if (request.fatherId().isPresent()) {

            Long fatherId = request.fatherId().orElse(null);

            person.setFather(
                    fatherId == null
                            ? null
                            : personRules.getMalePerson(
                                    fatherId,
                                    "Father must be male."));
        }

        if (request.motherId().isPresent()) {

            Long motherId = request.motherId().orElse(null);

            person.setMother(
                    motherId == null
                            ? null
                            : personRules.getFemalePerson(
                                    motherId,
                                    "Mother must be female."));
        }

        personRepository.save(person);
        entityManager.flush();

        return personDetailedViewRepository.findById(id).orElseThrow(() -> new NotFoundException("Person", id));
    }

    @Transactional
    public void deletePerson(Long id) {

        Person person = personRules.getPerson(id);
        personRepository.delete(person);
    }

    // Address

    @Transactional(readOnly = true)
    public Page<AddressResponse> getPersonAddresses(Long id, Pageable pageable) {
        personRules.getPerson(id);
        return personAddressRepository.findByPersonPersonId(id, pageable)
                .map(addressMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public AddressResponse getPersonAddress(Long personId, Long addressId) {
        return addressMapper.toResponse(addressRules.getAddressOfPerson(personId, addressId));
    }

    @Transactional
    public AddressResponse createAddress(Long id, AddressRequest request) {

        Person person = personRules.getPerson(id);
        District district = addressRules.getDistrict(request.districtId());

        PersonAddress address = addressMapper.toEntity(request);
        address.setDistrict(district);
        address.setPerson(person);

        boolean hasAddresses = personAddressRepository.existsByPersonPersonId(id);

        if (!hasAddresses) {
            // First address is always primary
            address.setPrimary(true);
        } else if (request.primary()) {
            personAddressRepository.clearPrimaryByPersonId(id);
            address.setPrimary(true);
        } else {
            address.setPrimary(false);
        }

        return addressMapper.toResponse(personAddressRepository.save(address));
    }

    @Transactional
    public void deleteAddress(Long personId, Long addressId) {
        PersonAddress address = addressRules.getAddressOfPerson(personId, addressId);
        personAddressRepository.delete(address);
        if (address.isPrimary()) {
            personAddressRepository
                    .findFirstByPersonPersonIdAndAddressIdNotOrderByCreatedAtAsc(personId, addressId)
                    .ifPresent(other -> other.setPrimary(true));
        }
    }

    // Email

    @Transactional(readOnly = true)
    public Page<EmailAddressResponse> getPersonEmails(Long id, Pageable pageable) {
        personRules.getPerson(id);
        return personEmailAddressRepository.findByPersonPersonId(id, pageable).map(emailMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public EmailAddressResponse getPersonEmail(Long personId, Long emailId) {
        return emailMapper.toResponse(personContactRules.getEmailOfPerson(personId, emailId));
    }

    @Transactional
    public EmailAddressResponse createEmail(Long id, EmailAddressRequest request) {

        Person person = personRules.getPerson(id);
        personContactRules.checkEmailUnique(request.emailAddress());

        PersonEmailAddress email = new PersonEmailAddress();
        email.setEmailAddress(request.emailAddress());
        email.setPerson(person);

        boolean hasEmails = personEmailAddressRepository.existsByPersonPersonId(id);

        if (!hasEmails) {
            // First email must always be primary
            email.setPrimary(true);
        } else if (request.primary()) {
            personEmailAddressRepository.clearPrimaryByPersonId(id);
            email.setPrimary(true);
        } else {
            email.setPrimary(false);
        }

        return emailMapper.toResponse(personEmailAddressRepository.save(email));
    }

    @Transactional
    public void deleteEmail(Long personId, Long emailId) {
        PersonEmailAddress email = personContactRules.getEmailOfPerson(personId, emailId);
        personEmailAddressRepository.delete(email);
        if (email.isPrimary()) {
            personEmailAddressRepository
                    .findFirstByPersonPersonIdAndEmailAddressIdNotOrderByCreatedAtAsc(personId, emailId)
                    .ifPresent(other -> {
                        other.setPrimary(true);
                        personEmailAddressRepository.save(other);
                    });
        }
    }

    // Phone

    @Transactional(readOnly = true)
    public Page<PhoneNumberResponse> getPersonPhones(Long id, Pageable pageable) {
        personRules.getPerson(id);
        return personPhoneNumberRepository.findByPersonPersonId(id, pageable).map(phoneMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public PhoneNumberResponse getPersonPhone(Long personId, Long phoneId) {
        return phoneMapper.toResponse(personContactRules.getPhoneOfPerson(phoneId, personId));
    }

    @Transactional
    public PhoneNumberResponse createPhone(Long id, PhoneNumberRequest request) {

        Person person = personRules.getPerson(id);

        personContactRules.checkPhoneUnique(request.phoneNumber());

        PersonPhoneNumber phone = new PersonPhoneNumber();
        phone.setPhoneNumber(request.phoneNumber());
        phone.setPerson(person);

        boolean hasPhones = personPhoneNumberRepository.existsByPersonPersonId(id);

        if (!hasPhones) {
            // First phone is always primary
            phone.setPrimary(true);
        } else if (request.primary()) {
            personPhoneNumberRepository.clearPrimaryByPersonId(id);
            phone.setPrimary(true);
        } else {
            phone.setPrimary(false);
        }

        return phoneMapper.toResponse(personPhoneNumberRepository.save(phone));
    }

    @Transactional
    public void deletePhone(Long personId, Long phoneId) {
        PersonPhoneNumber phone = personContactRules.getPhoneOfPerson(phoneId, personId);
        personPhoneNumberRepository.delete(phone);
        if (phone.isPrimary()) {
            personPhoneNumberRepository
                    .findFirstByPersonPersonIdAndPhoneNumberIdNotOrderByCreatedAtAsc(personId, phoneId)
                    .ifPresent(other -> other.setPrimary(true));
        }
    }

}
