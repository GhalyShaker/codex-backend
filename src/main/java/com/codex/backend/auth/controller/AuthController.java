package com.codex.backend.auth.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;

import com.codex.backend.auth.dto.AuthResponse;
import com.codex.backend.auth.dto.LoginRequest;
import com.codex.backend.auth.service.AuthService;
import com.codex.backend.system.api.ApiPaths;
import com.codex.backend.system.api.ApiVersion;

@RestController
@RequestMapping(ApiVersion.V1 + ApiPaths.AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(
                        request.getEmail(),
                        request.getPassword()));
    }
}
