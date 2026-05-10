package com.codex.backend.system.business.result;

import java.util.Map;

public record RuleViolation(

        String code,

        String message,

        Map<String, Object> metadata

) {

    public RuleViolation(
            String code,
            String message) {

        this(
                code,
                message,
                Map.of());

    }

}
