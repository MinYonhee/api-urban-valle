package com.realstate.imobiliaria.model.consultor;

import com.realstate.imobiliaria.model.imoveis.Imovel;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "consultores")
public class Consultor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "Nome não pode conter números") // Adicionado
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve conter @ e ser válido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "\\d{10,11}?", message = "Telefone deve conter 10 ou 11 dígitos")
    private String telefone;

    @ManyToMany(mappedBy = "consultores")
    private List<Imovel> imoveis = new ArrayList<>();

    @ManyToMany(mappedBy = "historicoConsultores")
    private List<Imovel> imoveisHistorico = new ArrayList<>();

    @ManyToMany(mappedBy = "comissoesConsultores")
    private List<Imovel> imoveisComissao = new ArrayList<>();

    public Consultor() {}

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public List<Imovel> getImoveis() { return imoveis; }
    public void setImoveis(List<Imovel> imoveis) { this.imoveis = imoveis; }
    public List<Imovel> getImoveisHistorico() { return imoveisHistorico; }
    public void setImoveisHistorico(List<Imovel> imoveisHistorico) { this.imoveisHistorico = imoveisHistorico; }
    public List<Imovel> getImoveisComissao() { return imoveisComissao; }
    public void setImoveisComissao(List<Imovel> imoveisComissao) { this.imoveisComissao = imoveisComissao; }

    public void addImovel(Imovel imovel) {
        if (!this.imoveis.contains(imovel)) {
            this.imoveis.add(imovel);
            imovel.getConsultores().add(this);
        }
    }

    public void removeImovel(Imovel imovel) {
        if (this.imoveis.remove(imovel)) {
            imovel.getConsultores().remove(this);
        }
    }

    public void addImovelHistorico(Imovel imovel) {
        if (!this.imoveisHistorico.contains(imovel)) {
            this.imoveisHistorico.add(imovel);
            imovel.getHistoricoConsultores().add(this);
        }
    }

    public void removeImovelHistorico(Imovel imovel) {
        if (this.imoveisHistorico.remove(imovel)) {
            imovel.getHistoricoConsultores().remove(this);
        }
    }

    public void addImovelComissao(Imovel imovel) {
        if (!this.imoveisComissao.contains(imovel)) {
            this.imoveisComissao.add(imovel);
            imovel.getComissoesConsultores().add(this);
        }
    }

    public void removeImovelComissao(Imovel imovel) {
        if (this.imoveisComissao.remove(imovel)) {
            imovel.getComissoesConsultores().remove(this);
        }
    }
}