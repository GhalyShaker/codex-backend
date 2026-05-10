package com.codex.backend.system.config;

import org.springframework.boot.autoconfigure.validation.ValidationConfigurationCustomizer;
import org.springframework.context.annotation.Configuration;

import com.codex.backend.system.validation.JsonNullableValueExtractor;

@Configuration
public class ValidationConfig implements ValidationConfigurationCustomizer {

    @Override
    public void customize(
            jakarta.validation.Configuration<?> configuration) {

        configuration.addValueExtractor(
                new JsonNullableValueExtractor()
        );
    }
}
