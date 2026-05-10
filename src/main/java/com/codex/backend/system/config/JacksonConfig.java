package com.codex.backend.system.config;

import org.openapitools.jackson.nullable.JsonNullable;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class JacksonConfig {

    @Bean
    Jackson2ObjectMapperBuilderCustomizer jsonNullableModule() {
        return builder -> builder.modulesToInstall(
                new JsonNullableModule());
    }

    @PostConstruct
    public void configure() {
        SpringDocUtils.getConfig()
                .replaceWithClass(JsonNullable.class, String.class);
    }
}
