package com.app.budget.infrastructure.controllers.ControllerExceptionHandler;

import com.app.budget.core.exceptions.AuthenticationException;
import com.app.budget.core.exceptions.UserException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<StandardError> userError(UserException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError(Instant.now(), status.value(), exception.getMessage(), request.getContextPath());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<StandardError> authError(AuthenticationException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError error = new StandardError(Instant.now(), status.value(), exception.getMessage(), request.getContextPath());
        return ResponseEntity.status(status).body(error);
    }


}
