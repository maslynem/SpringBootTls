package com.example.springboottls.exceptions;

public class CustomerNotFoundException extends RequestException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
