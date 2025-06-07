package com.realstate.imobiliaria.model.imoveis;

public enum TipoImovel {
    CASA("Casa Residencial"), APARTAMENTO("Apartamento"), TERRENO("Terreno"), COMERCIAL("Comercial");
    private final String descricao;
    TipoImovel(String descricao) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return descricao;
    }
}
