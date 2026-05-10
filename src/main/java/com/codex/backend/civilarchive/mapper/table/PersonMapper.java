package com.codex.backend.civilarchive.mapper.table;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.codex.backend.civilarchive.dto.request.CreatePersonRequest;
import com.codex.backend.civilarchive.dto.request.PatchPersonRequest;
import com.codex.backend.civilarchive.dto.request.PutPersonRequest;
import com.codex.backend.civilarchive.dto.response.PersonResponse;
import com.codex.backend.civilarchive.entity.table.Person;

@Mapper(componentModel = "spring", uses = {
                AddressMapper.class,
                ParentMapper.class,
                ContactMapper.class
})
public interface PersonMapper {

        @Mapping(target = "contacts", source = ".")
        PersonResponse toResponse(Person person);

        @Mapping(target = "personId", ignore = true)
        @Mapping(target = "nationalNumber", ignore = true)
        @Mapping(target = "mother", ignore = true)
        @Mapping(target = "father", ignore = true)
        @Mapping(target = "phoneNumbers", ignore = true)
        @Mapping(target = "emailAddresses", ignore = true)
        @Mapping(target = "addresses", ignore = true)
        Person toEntity(CreatePersonRequest request);

        default void updateFromPut(PutPersonRequest request, @MappingTarget Person person) {
                person.setFirstNameArabic(request.firstNameArabic());
                person.setMiddleNameArabic(request.middleNameArabic());
                person.setLastNameArabic(request.lastNameArabic());
                person.setFirstNameEnglish(request.firstNameEnglish());
                person.setMiddleNameEnglish(request.middleNameEnglish());
                person.setLastNameEnglish(request.lastNameEnglish());
                person.setGender(request.gender());
                person.setBirthDate(request.birthDate());
                person.setNationalNumber(request.nationalNumber());
                person.setBloodType(request.bloodType());
        }

        default void updateFromPatch(
                        PatchPersonRequest request,
                        @MappingTarget Person person) {

                if (request.firstNameArabic().isPresent()) {
                        person.setFirstNameArabic(request.firstNameArabic().get());
                }

                if (request.middleNameArabic().isPresent()) {
                        person.setMiddleNameArabic(request.middleNameArabic().get());
                }

                if (request.lastNameArabic().isPresent()) {
                        person.setLastNameArabic(request.lastNameArabic().get());
                }

                if (request.firstNameEnglish().isPresent()) {
                        person.setFirstNameEnglish(request.firstNameEnglish().get());
                }

                if (request.middleNameEnglish().isPresent()) {
                        person.setMiddleNameEnglish(request.middleNameEnglish().get());
                }

                if (request.lastNameEnglish().isPresent()) {
                        person.setLastNameEnglish(request.lastNameEnglish().get());
                }

                if (request.gender().isPresent()) {
                        person.setGender(request.gender().get());
                }

                if (request.birthDate().isPresent()) {
                        person.setBirthDate(request.birthDate().get());
                }

                if (request.nationalNumber().isPresent()) {
                        person.setNationalNumber(request.nationalNumber().get());
                }

                if (request.bloodType().isPresent()) {
                        person.setBloodType(request.bloodType().get());
                }
        }
}
