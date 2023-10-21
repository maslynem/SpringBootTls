package com.example.springboottls.controllers;

import com.example.springboottls.dto.ErrorDto;
import com.example.springboottls.exceptions.CompanyNotFoundException;
import com.example.springboottls.exceptions.CustomerNotFoundException;
import com.example.springboottls.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CustomerNotFoundException.class, CompanyNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorDto entityNotFoundExceptionHandler(EntityNotFoundException ex) {
        log.warn("Entity Not Found Exception: {}", ex.getMessage());
        return ErrorDto.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorDto unknownExceptionHandler(Exception ex) {
        log.error("Unknown exception: {}", ex.getMessage());
        return ErrorDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.joining("; "));
        log.warn("Valid Exception: {}", message);
        ErrorDto errorDto = ErrorDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .build();
        return ResponseEntity.badRequest().body(errorDto);
    }
}
