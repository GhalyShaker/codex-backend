package com.codex.backend.system.mapper;

import com.codex.backend.system.exception.AppException;

public interface ConstraintMapper {

    boolean supports(String constraintName);

    AppException map(String constraintName);
}
