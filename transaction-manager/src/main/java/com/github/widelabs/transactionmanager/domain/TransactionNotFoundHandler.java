package com.github.widelabs.transactionmanager.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class TransactionNotFoundHandler {

    @ExceptionHandler(TransactionNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handler(TransactionNotFound ex) {}
}
