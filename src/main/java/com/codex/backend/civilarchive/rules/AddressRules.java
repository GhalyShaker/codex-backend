package com.codex.backend.civilarchive.rules;

import org.springframework.stereotype.Component;

import com.codex.backend.civilarchive.entity.table.PersonAddress;
import com.codex.backend.civilarchive.repository.table.PersonAddressRepository;
import com.codex.backend.reference.entity.table.District;
import com.codex.backend.reference.rules.DistrictRules;
import com.codex.backend.system.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddressRules {

    private final DistrictRules districtRules;
    private final PersonAddressRepository personAddressRepository;

    public District getDistrict(Integer id) {
        return districtRules.getDistrict(id);
    }

    public void checkDistrictExists(Integer id) {
        districtRules.checkDistrictExists(id);
    }

    public PersonAddress getAddressOfPerson(Long personId, Long addressId) {
        return personAddressRepository.findByAddressIdAndPerson_PersonId(addressId, personId)
                .orElseThrow(() -> new NotFoundException(
                        "Address", addressId));
    }
}