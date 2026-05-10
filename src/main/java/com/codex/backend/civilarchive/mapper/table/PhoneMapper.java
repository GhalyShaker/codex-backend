package com.codex.backend.civilarchive.mapper.table;

import java.util.List;

import org.mapstruct.Mapper;

import com.codex.backend.civilarchive.dto.response.PhoneNumberResponse;
import com.codex.backend.civilarchive.entity.table.PersonPhoneNumber;

@Mapper(componentModel = "spring")
public interface PhoneMapper {

    PhoneNumberResponse toResponse(PersonPhoneNumber entity);

    List<PhoneNumberResponse> toResponseList(List<PersonPhoneNumber> entities);
}
