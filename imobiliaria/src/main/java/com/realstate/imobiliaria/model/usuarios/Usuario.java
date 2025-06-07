package com.realstate.imobiliaria.model.usuarios;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.realstate.imobiliaria.model.imoveis.Imovel;
import com.realstate.imobiliaria.model.consultor.Consultor;
import com.realstate.imobiliaria.model.contato.Contato;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios",
        uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "uk_usuario_email")})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "O e-mail é obrigatório")
    @Size(max = 255, message = "E-mail deve ter no máximo 255 caracteres")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, max = 255, message = "Senha deve ter entre 6 e 255 caracteres")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String senha;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "Nome deve conter apenas letras e espaços")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Telefone deve conter apenas números, com 10 ou 11 dígitos")
    private String telefone;

    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(regexp = "^\\d{11}$", message = "CPF deve conter exatamente 11 dígitos numéricos")
    @Column(nullable = false, unique = true)
    private String cpf;

    @OneToMany(mappedBy = "proprietario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imovel> imoveis = new ArrayList<>();

    @ManyToMany(mappedBy = "usuariosInteressados")
    private List<Imovel> imoveisInteressados = new ArrayList<>();

    @ManyToMany(mappedBy = "transacoes")
    private List<Imovel> imoveisTransacionados = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contato> contatos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultor_id", foreignKey = @ForeignKey(name = "fk_usuario_consultor"))
    private Consultor consultor;

    public Usuario() {}

    public void adicionarImovel(Imovel imovel) {
        this.imoveis.add(imovel);
        imovel.setProprietario(this);
    }

    public void removerImovel(Imovel imovel) {
        this.imoveis.remove(imovel);
        imovel.setProprietario(null);
    }

    public void adicionarContato(Contato contato) {
        this.contatos.add(contato);
        contato.setUsuario(this);
    }

    public void removerContato(Contato contato) {
        this.contatos.remove(contato);
        contato.setUsuario(null);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public List<Imovel> getImoveis() {
        return imoveis;
    }
    public void setImoveis(List<Imovel> imoveis) {
        this.imoveis = imoveis;
    }
    public List<Imovel> getImoveisInteressados() {
        return imoveisInteressados;
    }
    public void setImoveisInteressados(List<Imovel> imoveisInteressados) {
        this.imoveisInteressados = imoveisInteressados;
    }
    public List<Imovel> getImoveisTransacionados() {
        return imoveisTransacionados;
    }
    public void setImoveisTransacionados(List<Imovel> imoveisTransacionados) {
        this.imoveisTransacionados = imoveisTransacionados;
    }
    public List<Contato> getContatos() {
        return contatos;
    }
    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }
    public Consultor getConsultor() {
        return consultor;
    }
    public void setConsultor(Consultor consultor) {
        this.consultor = consultor;
    }
}
