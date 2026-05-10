package com.codex.backend.reference.rules;

import org.springframework.stereotype.Component;

import com.codex.backend.reference.entity.table.District;
import com.codex.backend.reference.repository.DistrictRepository;
import com.codex.backend.reference.repository.GovernorateRepository;
import com.codex.backend.system.exception.NotFoundException;

@Component
public class DistrictRules {
    private final DistrictRepository districtRepository;
    private final GovernorateRepository governorateRepository;

    public DistrictRules(DistrictRepository districtRepository, GovernorateRepository governorateRepository) {
        this.districtRepository = districtRepository;
        this.governorateRepository = governorateRepository;
    }

    public District getDistrict(Integer id) {
        return districtRepository.findById(id).orElseThrow(() -> this.districtNotFound(id));
    }

    public void checkDistrictExists(Integer integer) {
        if (!districtRepository.existsById(integer)) {
            this.districtNotFound(integer);
        }
    }

    private NotFoundException districtNotFound(Integer id) {
        return new NotFoundException("District", id);
    }
}
