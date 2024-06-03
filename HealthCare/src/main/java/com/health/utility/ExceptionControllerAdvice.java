package com.health.utility;

import com.health.exception.BadRequestException;
import com.health.exception.NotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorInfo> constraintViolationHandler(ConstraintViolationException exception) {
        ErrorInfo errorInfo = new ErrorInfo();
        String message = exception.getConstraintViolations().stream()
                                                            .map(ConstraintViolation::getMessage)
                                                            .collect(Collectors.joining(", "));

        errorInfo.setErrorMessage(message);
        errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorInfo.setLocalDateTime(LocalDateTime.now());

        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> exceptionHandler(MethodArgumentNotValidException exception) {
        ErrorInfo errorInfo = new ErrorInfo();
        String message = exception.getBindingResult().getAllErrors().stream()
                                                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                                            .collect(Collectors.joining(", "));

        errorInfo.setErrorMessage(message);
        errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorInfo.setLocalDateTime(LocalDateTime.now());

        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInfo> notFoundExceptionHandler(NotFoundException exception) {
        ErrorInfo errorInfo = new ErrorInfo();

        errorInfo.setErrorMessage(exception.getMessage());
        errorInfo.setErrorCode(HttpStatus.NOT_FOUND.value());
        errorInfo.setLocalDateTime(LocalDateTime.now());

        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorInfo> badRequestExceptionHandler(BadRequestException exception) {
        ErrorInfo errorInfo = new ErrorInfo();

        errorInfo.setErrorMessage(exception.getMessage());
        errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorInfo.setLocalDateTime(LocalDateTime.now());

        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}
