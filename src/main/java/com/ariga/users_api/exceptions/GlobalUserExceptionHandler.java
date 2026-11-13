package com.ariga.users_api.exceptions;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ariga.users_api.dto.ErrorResponse;

@RestControllerAdvice 
public class GlobalUserExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(UserNotFoundException ex){
        return  build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler (EmailAlreadyExistsException.class)
    @ResponseStatus (HttpStatus.CONFLICT)
    public ErrorResponse handleEmailConflic(EmailAlreadyExistsException ex){
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }
    @ExceptionHandler (MethodArgumentNotValidException.class)
    @ResponseStatus (HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(MethodArgumentNotValidException ex){
        String message = ex.getBindingResult().getFieldErrors().stream()
                            .map(error -> error.getField() + " " + error.getDefaultMessage())
                            .collect(Collectors.joining(", "));
                            return build(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler (DataIntegrityViolationException.class)
    @ResponseStatus (HttpStatus.CONFLICT)
    public ErrorResponse handleGeneralConflicts(DataIntegrityViolationException ex){
        return  build(HttpStatus.CONFLICT, "Data conflicts with an existing record");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUnreadable(HttpMessageNotReadableException ex) {
        return build(HttpStatus.BAD_REQUEST, "Malformed or invalid JSON request body");
    }


    private ErrorResponse build(HttpStatus Status, String message){
        return new ErrorResponse(Status.value(),Status.getReasonPhrase(),message,Instant.now());
    }
}
