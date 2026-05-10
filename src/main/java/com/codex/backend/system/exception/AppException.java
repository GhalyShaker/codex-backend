package com.codex.backend.system.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.codex.backend.system.api.ApiErrorDetail;
import com.codex.backend.system.error.ErrorCode;

public abstract class AppException extends RuntimeException {

    private final ErrorCode errorCode;

    private final HttpStatus status;

    private final List<ApiErrorDetail> details;

    protected AppException(
            ErrorCode errorCode,
            String message,
            HttpStatus status
    ) {
        this(errorCode, message, status, null);
    }

    protected AppException(
            ErrorCode errorCode,
            String message,
            HttpStatus status,
            List<ApiErrorDetail> details
    ) {
        super(message);

        this.errorCode = errorCode;
        this.status = status;
        this.details = details;
    }

    public String getErrorCode() {
        return errorCode.code();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<ApiErrorDetail> getDetails() {
        return details;
    }
}
