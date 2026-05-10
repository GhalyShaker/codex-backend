package com.codex.backend.system.business.validator;

import com.codex.backend.system.business.context.BusinessContext;

public interface BusinessValidator {

    void validate(
            BusinessContext context);

}
