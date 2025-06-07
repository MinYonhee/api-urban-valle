package com.realstate.imobiliaria.model.imoveis;

import com.realstate.imobiliaria.model.consultor.Consultor;
import com.realstate.imobiliaria.model.contato.Contato;
import com.realstate.imobiliaria.model.usuarios.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "imoveis",
        indexes = {
                @Index(columnList = "proprietario_id"),
                @Index(columnList = "preco"),
                @Index(columnList = "area"),
                @Index(columnList = "tipo")
        },
        uniqueConstraints = {@UniqueConstraint(columnNames = "titulo", name = "uk_imovel_titulo")}
)
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Size(min = 2, max = 255, message = "Título deve ter entre 2 e 255 caracteres")
    @Column(nullable = false)
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 2, max = 1000, message = "Descrição deve ter entre 2 e 1000 caracteres")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Positive(message = "Preço deve ser positivo")
    @Column(nullable = false)
    private Double preco;

    @NotBlank(message = "Endereço é obrigatório")
    @Size(min = 2, max = 255, message = "Endereço deve ter entre 2 e 255 caracteres")
    @Column(nullable = false)
    private String endereco;

    @NotNull(message = "Tipo é obrigatório")
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private TipoImovel tipo;

    @Min(value = 0, message = "Quartos deve ser zero ou positivo")
    @Column(nullable = false)
    private Short quartos;

    @Min(value = 0, message = "Banheiros deve ser zero ou positivo")
    @Column(nullable = false)
    private Short banheiros;

    @Positive(message = "Área deve ser positiva")
    @Column(nullable = false)
    private Double area;

    @NotNull(message = "Status é obrigatório")
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private StatusImovel status;

    @Size(max = 255, message = "URL da imagem deve ter no máximo 255 caracteres")
    @Pattern(regexp = "^(https?://)?[\\w\\-]+(\\.[\\w\\-]+)+[/#?]?.*$|^$", message = "URL da imagem inválida")
    @Column(name = "imagem_url")
    private String imagemUrl;

    @NotBlank(message = "Categoria é obrigatória")
    @Size(min = 2, max = 50, message = "Categoria deve ter entre 2 e 50 caracteres")
    @Column(nullable = false)
    private String categoria;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime dataCadastro;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "proprietario_id", foreignKey = @ForeignKey(name = "fk_imovel_proprietario"))
    private Usuario proprietario;

    @OneToMany(mappedBy = "imovel", cascade = CascadeType.ALL, orphanRemoval = true)
    @Size(max = 50, message = "Um imóvel pode ter no máximo 50 contatos associados")
    private List<Contato> contatos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "interesse_imovel",
            joinColumns = @JoinColumn(name = "id_imovel", foreignKey = @ForeignKey(name = "fk_interesse_imovel")),
            inverseJoinColumns = @JoinColumn(name = "id_usuario", foreignKey = @ForeignKey(name = "fk_interesse_usuario"))
    )
    @Size(max = 100, message = "Um imóvel pode ter no máximo 100 usuários interessados")
    private List<Usuario> usuariosInteressados = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "transacao_imovel",
            joinColumns = @JoinColumn(name = "id_imovel", foreignKey = @ForeignKey(name = "fk_transacao_imovel")),
            inverseJoinColumns = @JoinColumn(name = "id_usuario", foreignKey = @ForeignKey(name = "fk_transacao_usuario"))
    )
    private List<Usuario> transacoes = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "gestao_imovel_consultor",
            joinColumns = @JoinColumn(name = "id_imovel", foreignKey = @ForeignKey(name = "fk_gestao_imovel")),
            inverseJoinColumns = @JoinColumn(name = "id_consultor", foreignKey = @ForeignKey(name = "fk_gestao_consultor"))
    )
    private List<Consultor> consultores = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "historico_imovel_consultor",
            joinColumns = @JoinColumn(name = "id_imovel", foreignKey = @ForeignKey(name = "fk_historico_imovel")),
            inverseJoinColumns = @JoinColumn(name = "id_consultor", foreignKey = @ForeignKey(name = "fk_historico_consultor"))
    )
    private List<Consultor> historicoConsultores = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "comissao_imovel_consultor",
            joinColumns = @JoinColumn(name = "id_imovel", foreignKey = @ForeignKey(name = "fk_comissao_imovel")),
            inverseJoinColumns = @JoinColumn(name = "id_consultor", foreignKey = @ForeignKey(name = "fk_comissao_consultor"))
    )
    private List<Consultor> comissoesConsultores = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "relacao_imovel",
            joinColumns = @JoinColumn(name = "id_imovel_1", foreignKey = @ForeignKey(name = "fk_relacao_imovel_1")),
            inverseJoinColumns = @JoinColumn(name = "id_imovel_2", foreignKey = @ForeignKey(name = "fk_relacao_imovel_2"))
    )
    private List<Imovel> imoveisRelacionados = new ArrayList<>();

    public void adicionarContato(Contato contato) {
        this.contatos.add(contato);
        contato.setImovel(this);
    }

    public void removerContato(Contato contato) {
        this.contatos.remove(contato);
        contato.setImovel(null);
    }

    public void adicionarInteressado(Usuario usuario) {

        this.usuariosInteressados.add(usuario);
    }

    public void removerInteressado(Usuario usuario) {

        this.usuariosInteressados.remove(usuario);
    }

    public void adicionarTransacao(Usuario usuario) {

        this.transacoes.add(usuario);
    }

    public void adicionarConsultor(Consultor consultor) {
        if (!this.consultores.contains(consultor)) {
            this.consultores.add(consultor);
            consultor.getImoveis().add(this);
        }
    }

    public void removerConsultor(Consultor consultor) {
        if (this.consultores.remove(consultor)) {
            consultor.getImoveis().remove(this);
        }
    }

    public void adicionarConsultorAoHistorico(Consultor consultor) {
        if (!this.historicoConsultores.contains(consultor)) {
            this.historicoConsultores.add(consultor);
            consultor.getImoveisHistorico().add(this);
        }
    }

    public void removerConsultorDoHistorico(Consultor consultor) {
        if (this.historicoConsultores.remove(consultor)) {
            consultor.getImoveisHistorico().remove(this);
        }
    }

    public void adicionarConsultorComissao(Consultor consultor) {
        if (!this.comissoesConsultores.contains(consultor)) {
            this.comissoesConsultores.add(consultor);
            consultor.getImoveisComissao().add(this);
        }
    }

    public void removerConsultorComissao(Consultor consultor) {
        if (this.comissoesConsultores.remove(consultor)) {
            consultor.getImoveisComissao().remove(this);
        }
    }

    public void adicionarImovelRelacionado(Imovel imovel) {
        if (!this.imoveisRelacionados.contains(imovel) && !this.equals(imovel)) {
            this.imoveisRelacionados.add(imovel);
            imovel.imoveisRelacionados.add(this);
        }
    }

    public void removerImovelRelacionado(Imovel imovel) {
        this.imoveisRelacionados.remove(imovel);
        imovel.imoveisRelacionados.remove(this);
    }

    public Imovel() {}

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
    public TipoImovel getTipo() {
        return tipo;
    }
    public void setTipo(TipoImovel tipo) {
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
    public StatusImovel getStatus() {
        return status;
    }
    public void setStatus(StatusImovel status) {
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
    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    public Usuario getProprietario() {
        return proprietario;
    }
    public void setProprietario(Usuario proprietario) {
        this.proprietario = proprietario;
    }
    public List<Contato> getContatos() {
        return contatos;
    }
    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }
    public List<Usuario> getUsuariosInteressados() {
        return usuariosInteressados;
    }
    public void setUsuariosInteressados(List<Usuario> usuariosInteressados) {
        this.usuariosInteressados = usuariosInteressados;
    }
    public List<Usuario> getTransacoes() {
        return transacoes;
    }
    public void setTransacoes(List<Usuario> transacoes) {
        this.transacoes = transacoes;
    }
    public List<Consultor> getConsultores() {
        return consultores;
    }
    public void setConsultores(List<Consultor> consultores) {
        this.consultores = consultores;
    }
    public List<Consultor> getHistoricoConsultores() {
        return historicoConsultores;
    }
    public void setHistoricoConsultores(List<Consultor> historicoConsultores) {
        this.historicoConsultores = historicoConsultores;
    }
    public List<Consultor> getComissoesConsultores() {
        return comissoesConsultores;
    }
    public void setComissoesConsultores(List<Consultor> comissoesConsultores) {
        this.comissoesConsultores = comissoesConsultores;
    }
    public List<Imovel> getImoveisRelacionados() {
        return imoveisRelacionados;
    }
    public void setImoveisRelacionados(List<Imovel> imoveisRelacionados) {
        this.imoveisRelacionados = imoveisRelacionados;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Imovel imovel = (Imovel) o;
        return Objects.equals(id, imovel.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Imovel{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", preco=" + preco +
                ", endereco='" + endereco + '\'' +
                ", tipo=" + tipo +
                ", status=" + status +
                '}';
    }
}