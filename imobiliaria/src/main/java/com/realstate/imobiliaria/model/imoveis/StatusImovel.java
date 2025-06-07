package com.realstate.imobiliaria.model.imoveis;

public enum StatusImovel {
    DISPONIVEL("Disponível"), VENDIDO("Vendido"), ALUGADO("Alugado"), PENDENTE("Pendente");
    private final String descricao;
    StatusImovel(String descricao) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return descricao;
    }
}
