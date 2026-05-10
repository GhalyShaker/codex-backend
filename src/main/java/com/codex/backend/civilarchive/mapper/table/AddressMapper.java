package com.codex.backend.civilarchive.mapper.table;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.codex.backend.civilarchive.dto.request.AddressRequest;
import com.codex.backend.civilarchive.dto.response.AddressResponse;
import com.codex.backend.civilarchive.entity.table.PersonAddress;
import com.codex.backend.reference.mapper.DistrictMapper;
import com.codex.backend.reference.mapper.GovernorateMapper;

@Mapper(componentModel = "spring", uses = { GovernorateMapper.class, DistrictMapper.class })
public interface AddressMapper {

    AddressResponse toResponse(PersonAddress entity);

    List<AddressResponse> toResponseList(List<PersonAddress> entities);

    @Mapping(target = "district", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "primary", ignore = true)
    PersonAddress toEntity(AddressRequest request);
}
