package com.codex.backend.auth.service;

import java.time.Duration;
import java.time.Instant;

import org.springframework.stereotype.Service;

import com.codex.backend.auth.entity.Session;
import com.codex.backend.auth.repository.SessionRepository;
import com.codex.backend.identity.entity.table.User;
import com.codex.backend.security.token.SecureTokenGenerator;
import com.codex.backend.security.token.TokenHasher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final SecureTokenGenerator tokenGenerator;
    private final TokenHasher tokenHasher;

    public String createSession(User user) {

        String refreshToken = tokenGenerator.generate();

        String tokenHash = tokenHasher.hash(refreshToken);

        Session session = new Session(
                user,
                tokenHash,
                Instant.now().plus(Duration.ofDays(30)));

        sessionRepository.save(session);

        return refreshToken;
    }
}