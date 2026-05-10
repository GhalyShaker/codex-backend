package com.codex.backend.system.error;

public enum SystemErrorCode implements ErrorCode {

    VALIDATION_ERROR("SYSTEM.VALIDATION_ERROR"),

    INVALID_PARAMETER("SYSTEM.INVALID_PARAMETER"),

    INTERNAL_ERROR("SYSTEM.INTERNAL_ERROR"),

    UNAUTHORIZED("SYSTEM.UNAUTHORIZED"),

    FORBIDDEN("SYSTEM.FORBIDDEN"),

    DATA_INTEGRITY_ERROR("SYSTEM.DATA_INTEGRITY_ERROR"),

    DATABASE_ERROR("SYSTEM.DATABASE_ERROR"),

    RESOURCE_NOT_FOUND("SYSTEM.RESOURCE_NOT_FOUND");

    private final String code;

    SystemErrorCode(String code) {
        this.code = code;
    }

    @Override
    public String code() {
        return code;
    }
}
