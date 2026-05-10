package com.codex.backend.reference.mapper;

import org.mapstruct.Mapper;

import com.codex.backend.reference.dto.GovernorateResponse;
import com.codex.backend.reference.entity.table.Governorate;

@Mapper(componentModel = "spring")
public interface GovernorateMapper {

    GovernorateResponse toResponse(Governorate governorate);
}
