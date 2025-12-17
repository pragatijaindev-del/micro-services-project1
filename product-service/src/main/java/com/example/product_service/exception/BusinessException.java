package com.example.product_service.exception;

//custom exception for product-related business logic failures.

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
