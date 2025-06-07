package com.realstate.imobiliaria.exception;

public class ConsultorValidationException extends RuntimeException {

    public ConsultorValidationException(String message) {
        super(message);
    }

    public ConsultorValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}