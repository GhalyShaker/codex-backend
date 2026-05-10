package com.codex.backend.civilarchive.mapper.table;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.codex.backend.civilarchive.dto.response.ContactInformationsResponse;
import com.codex.backend.civilarchive.entity.table.Person;

@Mapper(componentModel = "spring", uses = {
        PhoneMapper.class,
        EmailMapper.class
})
public interface ContactMapper {

    @Mapping(target = "phoneNumbers", source = "phoneNumbers")
    @Mapping(target = "emailAddresses", source = "emailAddresses")
    ContactInformationsResponse toResponse(Person person);
}
