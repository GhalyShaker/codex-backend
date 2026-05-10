package com.codex.backend.system.api;

import java.time.Instant;
import java.util.List;

public class ApiErrorResponse {

    private Instant timestamp;

    private int status;

    private String code;

    private String message;

    private String path;

    private String correlationId;

    private List<ApiErrorDetail> details;

    public ApiErrorResponse() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public List<ApiErrorDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ApiErrorDetail> details) {
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
