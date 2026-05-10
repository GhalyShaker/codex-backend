package com.codex.backend.system.business.rule;

public record RuleDefinition(

        String code,

        String name,

        String description,

        RuleSeverity severity

) {
}
