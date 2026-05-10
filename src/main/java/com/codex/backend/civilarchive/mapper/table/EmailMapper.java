package com.codex.backend.civilarchive.mapper.table;

import java.util.List;

import org.mapstruct.Mapper;

import com.codex.backend.civilarchive.dto.response.EmailAddressResponse;
import com.codex.backend.civilarchive.entity.table.PersonEmailAddress;

@Mapper(componentModel = "spring")
public interface EmailMapper {

    EmailAddressResponse toResponse(PersonEmailAddress entity);

    List<EmailAddressResponse> toResponseList(List<PersonEmailAddress> entities);
}