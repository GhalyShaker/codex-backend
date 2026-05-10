package com.codex.backend.identity.service;

import com.codex.backend.identity.dto.UserLoginDTO;
import com.codex.backend.identity.entity.table.User;
import com.codex.backend.identity.repository.table.UserRepository;
import com.codex.backend.system.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdentityService {

    private final UserRepository userRepository;

    public UserLoginDTO getLoginDataByEmail(String email) {
        return userRepository.findLoginDataByEmail(email)
                .orElseThrow(() -> new NotFoundException("User_Email", email));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User_Email", email));
    }
}
