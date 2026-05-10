package com.codex.backend.system.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
public class PageableConfiguration {
    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer paginationCustomizer() {
        return pageable -> {
            pageable.setOneIndexedParameters(true);
            pageable.setMaxPageSize(100);
            pageable.setFallbackPageable(PageRequest.of(0, 20));
        };
    }
}
