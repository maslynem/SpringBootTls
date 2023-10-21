package com.example.springboottls.exceptions;

public class CompanyNotFoundException extends EntityNotFoundException {
    public CompanyNotFoundException(String message) {
        super(message);
    }
}
