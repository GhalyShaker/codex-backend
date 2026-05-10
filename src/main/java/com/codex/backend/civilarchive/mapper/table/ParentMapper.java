package com.codex.backend.civilarchive.mapper.table;

import org.mapstruct.Mapper;

import com.codex.backend.civilarchive.dto.response.ParentSummaryResponse;
import com.codex.backend.civilarchive.entity.table.Person;

@Mapper(componentModel = "spring")
public interface ParentMapper {

    ParentSummaryResponse toResponse(Person person);
}
