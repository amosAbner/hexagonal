package com.study.hexagonal.application.core.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String customerId) {
        super("Cliente não encontrado: " + customerId);
    }
}
