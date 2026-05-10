package com.codex.backend.system.exception;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.codex.backend.system.api.ApiErrorDetail;
import com.codex.backend.system.api.ApiErrorResponse;
import com.codex.backend.system.error.SystemErrorCode;
import com.codex.backend.system.mapper.ConstraintMapperRegistry;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

        private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
        private ConstraintMapperRegistry constraintMapperRegistry;

        @ExceptionHandler(AppException.class)
        public ResponseEntity<ApiErrorResponse> handleAppException(
                        AppException ex,
                        HttpServletRequest request) {

                ApiErrorResponse response = buildResponse(
                                ex.getStatus(),
                                ex.getErrorCode(),
                                ex.getMessage(),
                                request.getRequestURI(),
                                ex.getDetails());

                return ResponseEntity
                                .status(ex.getStatus())
                                .body(response);
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ApiErrorResponse> handleMessageNotReadable(
                        HttpMessageNotReadableException ex,
                        HttpServletRequest request) {

                Throwable cause = ex.getCause();

                if (cause instanceof InvalidFormatException ife) {

                        String field = ife.getPath()
                                        .stream()
                                        .map(JsonMappingException.Reference::getFieldName)
                                        .filter(Objects::nonNull)
                                        .findFirst()
                                        .orElse("unknown");

                        List<ApiErrorDetail> details = List.of(
                                        new ApiErrorDetail(
                                                        field,
                                                        "Invalid value '" + ife.getValue() + "'"));

                        ApiErrorResponse response = buildResponse(
                                        HttpStatus.BAD_REQUEST,
                                        SystemErrorCode.INVALID_PARAMETER.code(),
                                        "Validation failed",
                                        request.getRequestURI(),
                                        details);

                        return ResponseEntity.badRequest().body(response);
                }

                // Fallback for malformed JSON, missing braces, etc.
                ApiErrorResponse response = buildResponse(
                                HttpStatus.BAD_REQUEST,
                                SystemErrorCode.INVALID_PARAMETER.code(),
                                "Invalid request body",
                                request.getRequestURI(),
                                null);

                return ResponseEntity.badRequest().body(response);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiErrorResponse> handleValidation(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest request) {

                List<ApiErrorDetail> details = new ArrayList<>();

                details.addAll(
                                ex.getBindingResult()
                                                .getFieldErrors()
                                                .stream()       
                                                .map(fieldError -> new ApiErrorDetail(
                                                                fieldError.getField(),
                                                                fieldError.getDefaultMessage()))
                                                .toList());

                details.addAll(
                                ex.getBindingResult()
                                                .getGlobalErrors()
                                                .stream()
                                                .map(objectError -> new ApiErrorDetail(
                                                                objectError.getObjectName(),
                                                                objectError.getDefaultMessage()))
                                                .toList());

                ValidationException validationException = new ValidationException(details);

                return handleValidationException(validationException, request);
        }

        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<ApiErrorResponse> handleTypeMismatch(
                        MethodArgumentTypeMismatchException ex,
                        HttpServletRequest request) {

                String message = String.format(
                                "Invalid value '%s' for parameter '%s'. Expected type is '%s'.",
                                ex.getValue(),
                                ex.getName(),
                                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown");

                ApiErrorResponse response = buildResponse(
                                HttpStatus.BAD_REQUEST,
                                SystemErrorCode.INVALID_PARAMETER.code(),
                                message,
                                request.getRequestURI(),
                                null);

                return ResponseEntity
                                .badRequest()
                                .body(response);
        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<ApiErrorResponse> handleConstraintViolation(
                        ConstraintViolationException ex,
                        HttpServletRequest request) {

                List<ApiErrorDetail> details = ex.getConstraintViolations()
                                .stream()
                                .map(v -> new ApiErrorDetail(
                                                v.getPropertyPath().toString(),
                                                v.getMessage()))
                                .toList();

                ValidationException validationException = new ValidationException(details);

                return handleValidationException(validationException, request);
        }

        @ExceptionHandler(PropertyReferenceException.class)
        public ResponseEntity<ApiErrorResponse> handlePropertyReference(
                        PropertyReferenceException ex,
                        HttpServletRequest request) {

                List<ApiErrorDetail> details = List.of(
                                new ApiErrorDetail(
                                                ex.getPropertyName(),
                                                "Invalid sort field"));

                ApiErrorResponse response = buildResponse(
                                HttpStatus.BAD_REQUEST,
                                SystemErrorCode.INVALID_PARAMETER.code(),
                                "Invalid sort parameter",
                                request.getRequestURI(),
                                details);

                return ResponseEntity.badRequest().body(response);
        }

        private ResponseEntity<ApiErrorResponse> handleValidationException(
                        ValidationException ex,
                        HttpServletRequest request) {

                ApiErrorResponse response = buildResponse(
                                ex.getStatus(),
                                ex.getErrorCode(),
                                ex.getMessage(),
                                request.getRequestURI(),
                                ex.getDetails());

                return ResponseEntity
                                .status(ex.getStatus())
                                .body(response);
        }

        @ExceptionHandler(DataAccessException.class)
        public ResponseEntity<ApiErrorResponse> handleDatabase(
                        DataAccessException ex,
                        HttpServletRequest request) {

                log.error("Database error on {}", request.getRequestURI(), ex);

                Throwable root = ex.getMostSpecificCause();

                String code;
                String message;
                HttpStatus status;

                if (root instanceof PSQLException pgEx) {

                        switch (pgEx.getSQLState()) {

                                case "23502", // not null
                                                "23503", // fk
                                                "23505", // unique
                                                "23514" // check
                                -> {
                                        code = SystemErrorCode.DATA_INTEGRITY_ERROR.code();
                                        message = "Request violates data integrity constraints";
                                        status = HttpStatus.CONFLICT;
                                }

                                default -> {
                                        code = SystemErrorCode.DATA_INTEGRITY_ERROR.code();
                                        message = "Database operation failed";
                                        status = HttpStatus.INTERNAL_SERVER_ERROR;
                                }
                        }

                } else {
                        code = SystemErrorCode.DATA_INTEGRITY_ERROR.code();
                        message = "Database operation failed";
                        status = HttpStatus.INTERNAL_SERVER_ERROR;
                }

                return ResponseEntity
                                .status(status)
                                .body(buildResponse(
                                                status,
                                                code,
                                                message,
                                                request.getRequestURI(),
                                                null));
        }

        @ExceptionHandler(NoResourceFoundException.class)
        public ResponseEntity<ApiErrorResponse> handleNoResourceFound(
                        NoResourceFoundException ex,
                        HttpServletRequest request) {

                ApiErrorResponse response = buildResponse(
                                HttpStatus.NOT_FOUND,
                                SystemErrorCode.RESOURCE_NOT_FOUND.code(),
                                "Resource not found",
                                request.getRequestURI(),
                                null);

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(response);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiErrorResponse> handleUnexpected(
                        Exception ex,
                        HttpServletRequest request) {
                log.error("Unexpected exception while processing {}",
                                request.getRequestURI(), ex);

                ApiErrorResponse response = buildResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                SystemErrorCode.INTERNAL_ERROR.code(),
                                "Unexpected internal server error",
                                request.getRequestURI(),
                                null);

                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(response);
        }

        private ApiErrorResponse buildResponse(
                        HttpStatus status,
                        String code,
                        String message,
                        String path,
                        List<ApiErrorDetail> details) {

                ApiErrorResponse response = new ApiErrorResponse();

                response.setTimestamp(Instant.now());
                response.setStatus(status.value());
                response.setCode(code);
                response.setMessage(message);
                response.setPath(path);
                response.setDetails(details);

                return response;
        }

}
