package com.codex.backend.system.exception;

import org.springframework.http.HttpStatus;

import com.codex.backend.system.error.SystemErrorCode;

public class UnauthorizedException extends AppException {
    public UnauthorizedException(
            String message
    ) {
        super(SystemErrorCode.UNAUTHORIZED, message, HttpStatus.UNAUTHORIZED);
    }
}
