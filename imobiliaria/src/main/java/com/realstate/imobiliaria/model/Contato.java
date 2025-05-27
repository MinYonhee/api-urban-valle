package com.realstate.imobiliaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "contato")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve conter @ e ser válido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;

    @NotBlank(message = "Mensagem é obrigatória")
    @Size(min = 2, max = 1000, message = "Mensagem deve ter entre 2 e 1000 caracteres")
    private String mensagem;

    @NotBlank(message = "Status é obrigatório")
    @Size(min = 2, max = 50, message = "Status deve ter entre 2 e 50 caracteres")
    private String status;

    private LocalDateTime data;

    @ManyToOne(optional = true)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(optional = true)
    @JoinColumn(name = "imovel_id")
    private Imovel imovel;

    @ManyToOne(optional = true)
    @JoinColumn(name = "consultor_id")
    private Consultor consultor;

    public Contato() {}

    public Contato(Long id, String email, String mensagem, String status, LocalDateTime data,
                   Usuario usuario, Imovel imovel, Consultor consultor) {
        this.id = id;
        this.email = email;
        this.mensagem = mensagem;
        this.status = status;
        this.data = data;
        this.usuario = usuario;
        this.imovel = imovel;
        this.consultor = consultor;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Imovel getImovel() { return imovel; }
    public void setImovel(Imovel imovel) { this.imovel = imovel; }
    public Consultor getConsultor() { return consultor; }
    public void setConsultor(Consultor consultor) { this.consultor = consultor; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contato contato = (Contato) o;
        return Objects.equals(id, contato.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Contato{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", mensagem='" + mensagem + '\'' +
                ", status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}