package com.codex.backend.security.token;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class TokenHasher {


    public String hash(String token) {

        try {

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");


            byte[] hash =
                    digest.digest(
                            token.getBytes(StandardCharsets.UTF_8)
                    );


            return Base64.getEncoder()
                    .encodeToString(hash);


        } catch (Exception e) {

            throw new IllegalStateException(
                    "Failed to hash token",
                    e
            );
        }
    }
}