package com.codex.backend.system.business.validator;

import java.util.List;

import com.codex.backend.system.business.action.RuleFailureHandler;
import com.codex.backend.system.business.context.BusinessContext;
import com.codex.backend.system.business.execution.ExecutionMode;
import com.codex.backend.system.business.execution.RuleExecutor;
import com.codex.backend.system.business.rule.BusinessRule;

public class DefaultBusinessValidator
        implements BusinessValidator {

    private final List<BusinessRule> rules;

    private final RuleExecutor executor;

    private final ExecutionMode mode;

    private final RuleFailureHandler handler;

    public DefaultBusinessValidator(

            List<BusinessRule> rules,

            RuleExecutor executor,

            ExecutionMode mode,

            RuleFailureHandler handler

    ) {

        this.rules = rules;
        this.executor = executor;
        this.mode = mode;
        this.handler = handler;

    }

    @Override
    public void validate(
            BusinessContext context) {

        executor.execute(
                rules,
                context,
                mode,
                handler);

    }

}
