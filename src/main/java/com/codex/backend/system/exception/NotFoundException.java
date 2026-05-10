package com.codex.backend.system.exception;

import org.springframework.http.HttpStatus;

import com.codex.backend.system.error.SystemErrorCode;

public class NotFoundException extends AppException {

    public NotFoundException(
            String resource,
            Object id
    ) {
        super(
                SystemErrorCode.RESOURCE_NOT_FOUND,
                resource + " not found: " + id,
                HttpStatus.NOT_FOUND
        );
    }
}
