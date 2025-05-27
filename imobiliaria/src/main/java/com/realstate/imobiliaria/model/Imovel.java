package com.realstate.imobiliaria.model;

import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "imoveis")
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private Double preco;
    private String endereco;
    private String tipo;
    private Short quartos;
    private Short banheiros;
    private Double area;
    private String status;
    private String imagemUrl;
    private String categoria;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDate dataCadastro;

    // ✅ Relacionamento com Consultor
    @ManyToOne(optional = true)
    @JoinColumn(name = "consultor_id")
    private Consultor consultor;

    public Imovel() {}

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Short getQuartos() {
        return quartos;
    }

    public void setQuartos(Short quartos) {
        this.quartos = quartos;
    }

    public Short getBanheiros() {
        return banheiros;
    }

    public void setBanheiros(Short banheiros) {
        this.banheiros = banheiros;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public Consultor getConsultor() {
        return consultor;
    }

    public void setConsultor(Consultor consultor) {
        this.consultor = consultor;
    }
}
