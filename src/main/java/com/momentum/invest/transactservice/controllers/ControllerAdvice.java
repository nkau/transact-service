package com.momentum.invest.transactservice.controllers;

import com.momentum.invest.transactservice.dtos.ErrorResponse;
import com.momentum.invest.transactservice.exceptions.TransactServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TransactServiceException.class)
    protected ResponseEntity<ErrorResponse> handleAccountNotFoundException(TransactServiceException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
