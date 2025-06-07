package com.realstate.imobiliaria.exception;

public class ImovelNotFoundException extends RuntimeException {
    public ImovelNotFoundException(Long id) {
        super("Imóvel com ID " + id + " não encontrado.");
    }
}