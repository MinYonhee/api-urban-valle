package com.realstate.imobiliaria.exception;

public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException(Long id) {
        super("Usuário com ID " + id + " não encontrado.");
    }

    public UsuarioNotFoundException(String email) {
        super("Usuário com e-mail " + email + " não encontrado.");
    }
}