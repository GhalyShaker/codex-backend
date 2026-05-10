package com.codex.backend.system.business.rule;

import com.codex.backend.system.business.context.BusinessContext;
import com.codex.backend.system.business.result.RuleResult;

public interface BusinessRule {

    RuleDefinition definition();

    RuleResult evaluate(BusinessContext context);

}
