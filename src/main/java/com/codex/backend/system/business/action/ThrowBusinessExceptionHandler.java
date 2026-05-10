package com.codex.backend.system.business.action;



import java.util.List;

import com.codex.backend.system.business.result.RuleViolation;
import com.codex.backend.system.exception.BusinessException;


public class ThrowBusinessExceptionHandler
        implements RuleFailureHandler {


    @Override
    public void handle(
            List<RuleViolation> violations
    ){

        throw new BusinessException(null, null);

    }

}
