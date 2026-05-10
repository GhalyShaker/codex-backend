package com.codex.backend.system.exception;

import org.springframework.http.HttpStatus;

import com.codex.backend.system.error.ErrorCode;

public class ConflictException extends AppException {

    public ConflictException(
            ErrorCode code,
            String message
    ) {
        super(code, message, HttpStatus.CONFLICT);
    }
}
