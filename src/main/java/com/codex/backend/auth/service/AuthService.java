package com.codex.backend.auth.service;

import java.time.Instant;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codex.backend.auth.dto.AuthResponse;
import com.codex.backend.identity.dto.UserLoginDTO;
import com.codex.backend.identity.entity.table.User;
import com.codex.backend.identity.service.IdentityService;
import com.codex.backend.security.jwt.JwtService;
import com.codex.backend.system.exception.UnauthorizedException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IdentityService identityService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final SessionService sessionService;

    public AuthResponse login(String email, String password) {

        UserLoginDTO loginData = identityService.getLoginDataByEmail(email);

        if (!passwordEncoder.matches(
                password,
                loginData.passwordHash())) {

            throw new UnauthorizedException(
                    "wrong email or password");
        }

        User user = identityService.getUserByEmail(email);

        String accessToken = jwtService.generateToken(
                user.getEmail(),
                user.getUsername(),
                user.getId());

        String refreshToken = sessionService.createSession(user);

        return new AuthResponse(
                accessToken,
                refreshToken,
                "Bearer",
                user.getEmail(),
                user.getUsername(),
                user.getId().longValue(),
                Instant.now().plusSeconds(60 * 15));
    }
}
