package com.codex.backend.reference.rules;

import org.springframework.stereotype.Component;

import com.codex.backend.reference.repository.GovernorateRepository;
import com.codex.backend.system.exception.NotFoundException;

@Component
public class GovernorateRules {
    private final GovernorateRepository governorateRepository;

    public GovernorateRules(GovernorateRepository governorateRepository) {
        this.governorateRepository = governorateRepository;
    }

    public void checkGovernorateExists(Integer id) {
        if (!governorateRepository.existsById(id)) {
            this.governorateNotFound(id);
        }
    }

    private NotFoundException governorateNotFound(Integer id) {
        return new NotFoundException("Governorate", id);
    }
}
