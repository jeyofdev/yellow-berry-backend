package com.jeyofdev.yellow_berry.exception;

import com.jeyofdev.yellow_berry.exception.model.ErrorResponse;
import com.jeyofdev.yellow_berry.util.Helper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * to handle the case when a NotFoundException is thrown
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exception, HttpServletRequest request) {
        return handleException(exception, HttpStatus.NOT_FOUND, request, null);
    }

    /**
     * to handle the case when a EntityNotFoundException is thrown
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> EntityNotFoundException(EntityNotFoundException exception, HttpServletRequest request) {
        return handleException(exception, HttpStatus.NOT_FOUND, request, null);
    }

    /**
     * to handle the case when the user is not found.
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException exception, HttpServletRequest request) {
        return handleException(exception, HttpStatus.NOT_FOUND, request, null);
    }

    /**
     * to handle the case when the provided username already exists in the database.
     */
    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException exception, HttpServletRequest request) {
        return handleException(exception, HttpStatus.BAD_REQUEST, request, null);
    }
    /**
     * to handle the case when the provided username or password are incorrect.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException exception, HttpServletRequest request) {
        return handleException(exception, HttpStatus.BAD_REQUEST, request, null);
    }
    /**
     * to handle the case when the user does not have the necessary roles
     * to access the requested resource.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException exception, HttpServletRequest request) {
        return handleException(exception, HttpStatus.UNAUTHORIZED, request, null);
    }

    /**
     * to handle the case where a method was invoked while the object was not in a suitable state to execute it
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException exception, HttpServletRequest request) {
        return handleException(exception, HttpStatus.BAD_REQUEST, request, null);
    }

    /**
     * to handle the case where a method was invoked while the provided token has expired
     */
    @ExceptionHandler(ExpireTokenException.class)
    public ResponseEntity<ErrorResponse> handleExpireTokenException(ExpireTokenException exception, HttpServletRequest request) {
        return handleException(exception, HttpStatus.BAD_REQUEST, request, null);
    }

    /**
     * to handle the case where a method was invoked while the token is missing or invalid
     */
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException exception, HttpServletRequest request) {
        return handleException(exception, HttpStatus.BAD_REQUEST, request, null);
    }

    /**
     * to handle the case where a method was called with one or more arguments that failed validation
     */
    @ExceptionHandler(BadValidationArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadValidationArgumentException(BadValidationArgumentException exception, HttpServletRequest request) {
        return handleException(exception, HttpStatus.BAD_REQUEST, request, null);
    }
    /**
     * to handle the case where a method was called with one or more invalid arguments
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException exception, HttpServletRequest request) {
        String errorMessage = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList().getFirst();

        return handleException(exception, HttpStatus.BAD_REQUEST, request, errorMessage);
    }

    /**
     * to handle the case where a method was called with one or more invalid arguments
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception, HttpServletRequest request) {
        return handleException(exception, HttpStatus.BAD_REQUEST, request, null);
    }

    /**
     * to handle the case where a when a requested resource (such as a database entity) is not found.
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException exception, HttpServletRequest request) {
        return handleException(exception, HttpStatus.BAD_REQUEST, request, null);
    }

    /**
     * to handle the case where a when a method is not implemented.
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedOperationException(UnsupportedOperationException exception, HttpServletRequest request) {
        return handleException(exception, HttpStatus.BAD_REQUEST, request, null);
    }

    /**
     * to handle the case where there is a problem with the request body.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        return handleException(exception, HttpStatus.BAD_REQUEST, request, null);
    }

    /**
     * Others
     */
    private ResponseEntity<ErrorResponse> handleException(Exception exception, HttpStatus status, HttpServletRequest request, String message) {
        exception.printStackTrace();

        String finalMessage = (message != null && !message.isEmpty())
                ? message
                : exception.getMessage().trim().replaceAll(";$", "")
                .split(";")[0].trim();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(finalMessage)
                .status(status.value())
                .exceptionName(exception.getClass().getSimpleName())
                .date(Helper.simpleDateFormat())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(errorResponse, status);
    }
}
