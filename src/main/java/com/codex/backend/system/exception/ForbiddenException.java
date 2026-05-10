package com.codex.backend.system.exception;

import org.springframework.http.HttpStatus;

import com.codex.backend.system.error.SystemErrorCode;

public class ForbiddenException extends AppException {

    public ForbiddenException(String message) {
        super(SystemErrorCode.FORBIDDEN, message, HttpStatus.FORBIDDEN);
    }

}
