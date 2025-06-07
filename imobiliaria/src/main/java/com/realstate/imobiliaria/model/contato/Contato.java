package com.realstate.imobiliaria.model.contato;

import com.realstate.imobiliaria.model.consultor.Consultor;
import com.realstate.imobiliaria.model.imoveis.Imovel;
import com.realstate.imobiliaria.model.usuarios.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

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

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private StatusContato status;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imovel_id")
    private Imovel imovel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultor_id")
    private Consultor consultor;

    public Contato() {}

    public Contato(Long id, String email, String mensagem, StatusContato status, LocalDateTime data,
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
    public String getMensagem() {
        return mensagem;
    }
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    public StatusContato getStatus() {
        return status;
    }
    public void setStatus(StatusContato status) {
        this.status = status;
    }
    public LocalDateTime getData() {
        return data;
    }
    public void setData(LocalDateTime data) {
        this.data = data;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Imovel getImovel() {
        return imovel;
    }
    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }
    public Consultor getConsultor() {
        return consultor;
    }
    public void setConsultor(Consultor consultor) {
        this.consultor = consultor;
    }

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
                ", status=" + status +
                ", data=" + data +
                '}';
    }
}