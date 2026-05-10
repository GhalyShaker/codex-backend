package com.codex.backend.system.api;

public class ApiErrorDetail {

    private String field;

    private String message;

    public ApiErrorDetail() {
    }

    public ApiErrorDetail(
            String field,
            String message
    ) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
