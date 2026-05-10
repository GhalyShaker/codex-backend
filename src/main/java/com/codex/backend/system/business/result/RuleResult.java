package com.codex.backend.system.business.result;

import java.util.List;

public interface RuleResult {

    boolean passed();

    List<RuleViolation> violations();

    static RuleResult success() {

        return new DefaultRuleResult(
                true,
                List.of());

    }

    static RuleResult failure(
            RuleViolation violation) {

        return new DefaultRuleResult(
                false,
                List.of(violation));

    }

    static RuleResult failure(
            List<RuleViolation> violations) {

        return new DefaultRuleResult(
                false,
                violations);

    }

}
