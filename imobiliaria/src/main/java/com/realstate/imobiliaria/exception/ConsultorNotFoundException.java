package com.realstate.imobiliaria.exception;

public class ConsultorNotFoundException extends RuntimeException {

    public ConsultorNotFoundException(Long id) {
        super("Consultor com ID " + id + " não encontrado.");
    }

    public ConsultorNotFoundException(String message) {
        super(message);
    }

    public ConsultorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}