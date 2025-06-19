package com.ecommerce.amarte.exception;

public class StockExceededException extends RuntimeException {
    public StockExceededException(String message) {
        super(message);
    }
}
