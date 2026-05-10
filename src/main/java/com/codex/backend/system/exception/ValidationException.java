package com.codex.backend.system.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.codex.backend.system.api.ApiErrorDetail;
import com.codex.backend.system.error.SystemErrorCode;

public class ValidationException extends AppException {

    public ValidationException(
            List<ApiErrorDetail> details
    ) {
        super(
                SystemErrorCode.VALIDATION_ERROR,
                "Validation failed",
                HttpStatus.BAD_REQUEST,
                details
        );
    }
}
