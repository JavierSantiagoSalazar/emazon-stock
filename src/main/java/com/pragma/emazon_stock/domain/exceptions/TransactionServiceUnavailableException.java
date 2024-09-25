package com.pragma.emazon_stock.domain.exceptions;

public class TransactionServiceUnavailableException extends RuntimeException {
    public TransactionServiceUnavailableException(String message) {
        super(message);
    }
}
