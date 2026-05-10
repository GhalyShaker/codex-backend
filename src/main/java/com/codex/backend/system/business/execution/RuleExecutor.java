package com.codex.backend.system.business.execution;

import java.util.ArrayList;
import java.util.List;

import com.codex.backend.system.business.action.RuleFailureHandler;
import com.codex.backend.system.business.context.BusinessContext;
import com.codex.backend.system.business.result.RuleResult;
import com.codex.backend.system.business.result.RuleViolation;
import com.codex.backend.system.business.rule.BusinessRule;

public class RuleExecutor {

    public void execute(

            List<BusinessRule> rules,

            BusinessContext context,

            ExecutionMode mode,

            RuleFailureHandler handler

    ) {

        List<RuleViolation> violations = new ArrayList<>();

        for (BusinessRule rule : rules) {

            RuleResult result = rule.evaluate(context);

            if (!result.passed()) {

                violations.addAll(
                        result.violations());

                if (mode == ExecutionMode.FAIL_FAST) {

                    break;

                }

            }

        }

        if (!violations.isEmpty()) {

            handler.handle(
                    violations);

        }

    }

}
