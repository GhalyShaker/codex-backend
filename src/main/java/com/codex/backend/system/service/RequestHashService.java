package com.codex.backend.system.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RequestHashService {
    private final ObjectMapper mapper;

    public String hash(Object request) {
        try {

            String json = mapper.writeValueAsString(request);

            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] bytes = digest.digest(json.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(bytes);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
