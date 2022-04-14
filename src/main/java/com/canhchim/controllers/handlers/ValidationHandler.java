package com.canhchim.controllers.handlers;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(
            ConstraintViolationException e,
            HttpServletResponse response,
            HttpServletRequest request) throws IOException
    {
        System.out.println("ERROR!");
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleMethodArgumentNotValid(
            @NotNull MethodArgumentNotValidException ex,
            @NotNull HttpHeaders headers, @NotNull HttpStatus status,
            @NotNull WebRequest request)
    {
        List<String> errors = new ArrayList<String>();
        for ( FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for ( ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(
                ex, apiError, headers, apiError.getStatus(), request);
    }

    @Getter @Setter
    public static class ApiError {

        private final HttpStatus status;
        private final String message;
        private final List<String> errors;

        public ApiError(HttpStatus status, String message, List<String> errors) {
            super();
            this.status = status;
            this.message = message;
            this.errors = errors;
        }

        public ApiError(HttpStatus status, String message, String error) {
            super();
            this.status = status;
            this.message = message;
            errors = List.of(error);
        }
    }
}
