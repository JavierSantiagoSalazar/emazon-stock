package com.pragma.emazon_stock.domain.exceptions;

public class JwtIsEmptyException extends RuntimeException{
    public JwtIsEmptyException(String message) {
        super(message);
    }
}
