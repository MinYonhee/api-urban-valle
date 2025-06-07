package com.realstate.imobiliaria.exception;

public class ContatoNotFoundException extends RuntimeException {
    public ContatoNotFoundException(Long id) {
        super("Contato com ID " + id + " não encontrado");
    }
}