package com.codex.backend.system.business.result;

import java.util.List;

public record DefaultRuleResult(

        boolean passed,

        List<RuleViolation> violations

) implements RuleResult {

}
