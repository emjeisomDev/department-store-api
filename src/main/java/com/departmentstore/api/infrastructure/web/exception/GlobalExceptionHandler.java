package com.departmentstore.api.infrastructure.web.exception;

import com.departmentstore.api.domain.exception.*;
import com.departmentstore.api.infrastructure.web.dto.response.ErrorResponseDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // =========================================================
    // 404 - NOT FOUND
    // =========================================================
    @ExceptionHandler({
            PersonNotFoundException.class,
            EmployeeNotFoundException.class,
            ClientNotFoundException.class,
            ResponsibleNotFoundException.class
    })
    public ResponseEntity<ErrorResponseDto> handleNotFound(final RuntimeException ex) {
        return buildResponse(
                HttpStatus.NOT_FOUND,
                "RESOURCE_NOT_FOUND",
                ex.getMessage(),
                Map.of()
        );
    }

    // =========================================================
    // 409 - CONFLICT
    // =========================================================
    @ExceptionHandler({
            PersonTypeConflictException.class,
            DuplicateTaxIdException.class,
            DuplicateRegistrationException.class
    })
    public ResponseEntity<ErrorResponseDto> handleConflict(final RuntimeException ex) {
        return buildResponse(
                HttpStatus.CONFLICT,
                "BUSINESS_CONFLICT",
                ex.getMessage(),
                Map.of()
        );
    }

    // =========================================================
    // 422 - BUSINESS RULE VIOLATION
    // =========================================================
    @ExceptionHandler({
            EmployeeMustBeNaturalPersonException.class,
            ResponsibleRequiredException.class
    })
    public ResponseEntity<ErrorResponseDto> handleBusinessRuleViolation(final RuntimeException ex) {
        return buildResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "BUSINESS_RULE_VIOLATION",
                ex.getMessage(),
                Map.of()
        );
    }

    // =========================================================
    // 400 - DTO VALIDATION
    // =========================================================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidation(final MethodArgumentNotValidException ex) {

        Map<String, Object> errors = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "VALIDATION_ERROR",
                "Request validation failed",
                errors
        );
    }

    // =========================================================
    // 400 - CONSTRAINT VALIDATION
    // =========================================================
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolation(final ConstraintViolationException ex) {

        Map<String, Object> details = Map.of(
                "violations",
                ex.getConstraintViolations()
                        .stream()
                        .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                        .toList()
        );
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "CONSTRAINT_VIOLATION",
                ex.getMessage(),
                details
        );
    }

    // =========================================================
    // 409 - DATABASE CONSTRAINT
    // =========================================================
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleDatabaseConflict(final DataIntegrityViolationException ex) {
        return buildResponse(
                HttpStatus.CONFLICT,
                "DATA_INTEGRITY_VIOLATION",
                "Database constraint violation",
                Map.of("cause",
                        ex.getMostSpecificCause().getMessage())
        );
    }

    // =========================================================
    // 500 - GENERIC ERROR
    // =========================================================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneric(final Exception ex) {
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred",
                Map.of(
                        "exception",
                        ex.getClass().getSimpleName()
                )
        );
    }

    // =========================================================
    // CENTRALIZAÇÃO DA CRIAÇÃO DO PAYLOAD
    // =========================================================
    private ResponseEntity<ErrorResponseDto> buildResponse(
            final HttpStatus status,
            final String code,
            final String message,
            final Map<String, Object> details)
    {

        return ResponseEntity
                .status(status)
                .body(new ErrorResponseDto(
                    code,
                    message,
                    details,
                    LocalDateTime.now())
                );
    }
}