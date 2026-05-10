package com.codex.backend.system.business.action;

import java.util.List;

import com.codex.backend.system.business.result.RuleViolation;

public interface RuleFailureHandler {

    void handle(
            List<RuleViolation> violations);

}
