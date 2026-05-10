package com.codex.backend.reference.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.codex.backend.reference.dto.DistrictResponse;
import com.codex.backend.reference.entity.table.District;

@Mapper(componentModel = "spring")
public interface DistrictMapper {

    @Mapping(source = "district.governorate.governorateId", target = "governorateId")
    DistrictResponse toResponse(District district);

    District toResponse(Integer districtId);
}
