package com.tuempresa.ordenes.domain.exception;

public class OrdenException extends RuntimeException {
    
    public OrdenException(String message) {
        super(message);
    }
    
    public OrdenException(String message, Throwable cause) {
        super(message, cause);
    }
} 