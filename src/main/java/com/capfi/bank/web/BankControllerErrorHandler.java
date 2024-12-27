package com.capfi.bank.web;

import com.capfi.bank.model.Error;
import com.capfi.bank.service.NoBalanceException;
import com.capfi.bank.service.NotEnoughBalanceException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class BankControllerErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(NoBalanceException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Error handleNoBalanceException(NoBalanceException ex) {
        return new Error(ex.getMessage());
    }

    @ExceptionHandler(NotEnoughBalanceException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Error handleNotEnoughBalanceException(NotEnoughBalanceException ex) {
        return new Error(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleException(Exception ex) {
        return new Error("An internal error occurred :" + ex.getMessage());
    }
}
