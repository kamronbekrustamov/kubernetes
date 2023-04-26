package com.kamronbek.users.controller;

import com.kamronbek.users.error.ApiError;
import com.kamronbek.users.error.ApiSubError;
import com.kamronbek.users.error.ApiValidationError;
import com.kamronbek.users.error.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgNotValid(MethodArgumentNotValidException exception) {
        List<ApiSubError> validationErrors = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> ApiValidationError.builder()
                        .object(fieldError.getObjectName())
                        .field(fieldError.getField())
                        .rejectedValue(fieldError.getRejectedValue())
                        .message(fieldError.getDefaultMessage())
                        .build()
                ).collect(Collectors.toList());
        return new ApiError(HttpStatus.BAD_REQUEST.value(), "Validation Errors", validationErrors);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(NotFoundException exception) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleInternalServerError(Exception exception) {
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
    }
}