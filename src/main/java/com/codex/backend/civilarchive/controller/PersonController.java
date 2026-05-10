package com.codex.backend.civilarchive.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.codex.backend.civilarchive.dto.filter.PersonFilter;
import com.codex.backend.civilarchive.dto.request.AddressRequest;
import com.codex.backend.civilarchive.dto.request.CreatePersonRequest;
import com.codex.backend.civilarchive.dto.request.EmailAddressRequest;
import com.codex.backend.civilarchive.dto.request.PatchPersonRequest;
import com.codex.backend.civilarchive.dto.request.PhoneNumberRequest;
import com.codex.backend.civilarchive.dto.request.PutPersonRequest;
import com.codex.backend.civilarchive.dto.response.AddressResponse;
import com.codex.backend.civilarchive.dto.response.EmailAddressResponse;
import com.codex.backend.civilarchive.dto.response.PhoneNumberResponse;
import com.codex.backend.civilarchive.entity.view.PersonDetailedView;
import com.codex.backend.civilarchive.service.table.PersonService;
import com.codex.backend.system.api.ApiPaths;
import com.codex.backend.system.api.ApiVersion;
import com.codex.backend.system.service.IdempotencyService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.time.Duration;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiVersion.V1 + ApiPaths.CIVIL_ARCHIVE + ApiPaths.CivilArchive.PersonPath.BASE)
@Validated
public class PersonController {

    private final PersonService personService;
    private final IdempotencyService idempotencyService;

    @GetMapping
    public Page<PersonDetailedView> getPersonsDetailedSearch(@ModelAttribute PersonFilter filter, Pageable pageable) {
        return personService.searchPersonsDetailed(filter, pageable);
    }

    @GetMapping("/{id}")
    public PersonDetailedView getPersonDetailed(@PathVariable @Min(1) Long id) {
        return personService.getPersonDetailed(id);
    }

    @PostMapping
    public ResponseEntity<Void> createPerson(
            @RequestHeader("Idempotency-Key") UUID key,
            @Valid @RequestBody CreatePersonRequest request) {

        Long personId = idempotencyService.execute(
                key,
                "CREATE_PERSON",
                request,
                Duration.ofHours(24),
                Long.class,
                () -> personService.createPerson(request));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(personId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDetailedView> updatePerson(
            @PathVariable @Min(1) Long id,
            @Valid @RequestBody PutPersonRequest request) {

        PersonDetailedView person = personService.updatePerson(id, request);
        return ResponseEntity.ok(person);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PersonDetailedView> patchPerson(
            @PathVariable @Min(1) Long id,
            @Valid @RequestBody PatchPersonRequest request) {
        PersonDetailedView person = personService.patchPerson(id, request);
        return ResponseEntity.ok(person);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable @Min(1) Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    // address

    @GetMapping("/{id}/addresses")
    public Page<AddressResponse> getPersonAddresses(@PathVariable @Min(1) Long id, Pageable pageable) {
        return personService.getPersonAddresses(id, pageable);
    }

    @GetMapping("/{id}/addresses/{addressId}")
    public ResponseEntity<AddressResponse> getPersonAddress(
            @PathVariable @Min(1) Long id,
            @PathVariable @Min(1) Long addressId) {
        return ResponseEntity.ok(personService.getPersonAddress(id, addressId));
    }

    @PostMapping("/{id}/addresses")
    public ResponseEntity<AddressResponse> createAddress(
            @RequestHeader("Idempotency-Key") UUID key,
            @PathVariable @Min(1) Long id,
            @Valid @RequestBody AddressRequest request) {

        AddressResponse addressResponse = idempotencyService.execute(
            key,
            "CREATE_ADDRESS",
            request,
            Duration.ofHours(24),
            AddressResponse.class,
            () -> personService.createAddress(id, request));

        return ResponseEntity.ok(addressResponse);
    }

    @DeleteMapping("/{id}/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable @Min(1) Long id,
            @PathVariable @Min(1) Long addressId) {
        personService.deleteAddress(id, addressId);
        return ResponseEntity.noContent().build();
    }

    // email

    @GetMapping("/{id}/emails")
    public Page<EmailAddressResponse> getPersonEmails(@PathVariable @Min(1) Long id, Pageable pageable) {
        return personService.getPersonEmails(id, pageable);
    }

    @GetMapping("/{id}/emails/{emailId}")
    public ResponseEntity<EmailAddressResponse> getPersonEmail(
            @PathVariable @Min(1) Long id,
            @PathVariable @Min(1) Long emailId) {
        return ResponseEntity.ok(personService.getPersonEmail(id, emailId));
    }

    @PostMapping("/{id}/emails")
    public ResponseEntity<EmailAddressResponse> createEmail(
            @PathVariable @Min(1) Long id,
            @Valid @RequestBody EmailAddressRequest request) {

        EmailAddressResponse email = personService.createEmail(id, request);
        return ResponseEntity.ok(email);
    }

    @DeleteMapping("/{id}/emails/{emailId}")
    public ResponseEntity<Void> deleteEmail(
            @PathVariable @Min(1) Long id,
            @PathVariable @Min(1) Long emailId) {
        personService.deleteEmail(id, emailId);
        return ResponseEntity.noContent().build();
    }

    // phone

    @GetMapping("/{id}/phones")
    public Page<PhoneNumberResponse> getPersonPhones(@PathVariable @Min(1) Long id, Pageable pageable) {
        return personService.getPersonPhones(id, pageable);
    }

    @GetMapping("/{id}/phones/{phoneNumberId}")
    public ResponseEntity<PhoneNumberResponse> getPersonPhone(
            @PathVariable @Min(1) Long id,
            @PathVariable @Min(1) Long phoneNumberId) {
        return ResponseEntity.ok(personService.getPersonPhone(id, phoneNumberId));
    }

    @PostMapping("/{id}/phones")
    public ResponseEntity<PhoneNumberResponse> createPhone(
            @PathVariable @Min(1) Long id,
            @Valid @RequestBody PhoneNumberRequest request) {

        PhoneNumberResponse phone = personService.createPhone(id, request);
        return ResponseEntity.ok(phone);
    }

    @DeleteMapping("/{id}/phones/{phoneNumberId}")
    public ResponseEntity<Void> deletePhone(
            @PathVariable @Min(1) Long id,
            @PathVariable @Min(1) Long phoneNumberId) {
        personService.deletePhone(id, phoneNumberId);
        return ResponseEntity.noContent().build();
    }
}
