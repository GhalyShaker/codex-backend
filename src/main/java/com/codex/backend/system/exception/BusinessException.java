package com.codex.backend.system.exception;

import org.springframework.http.HttpStatus;

import com.codex.backend.system.error.ErrorCode;

public class BusinessException extends AppException {

    public BusinessException(
            ErrorCode code,
            String message
    ) {
        super(code, message, HttpStatus.BAD_REQUEST);
    }
}
