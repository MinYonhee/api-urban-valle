package com.realstate.imobiliaria.repository;

import com.realstate.imobiliaria.model.imoveis.Imovel;
import com.realstate.imobiliaria.model.imoveis.StatusImovel;
import com.realstate.imobiliaria.model.imoveis.TipoImovel;
import com.realstate.imobiliaria.model.usuarios.Usuario;
import com.realstate.imobiliaria.model.consultor.Consultor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long> {

    Page<Imovel> findByProprietario(Usuario proprietario, Pageable pageable);

    @Query("SELECT i FROM Imovel i WHERE i.proprietario = :proprietario AND i.status = :status")
    List<Imovel> findByProprietarioAndStatus(@Param("proprietario") Usuario proprietario, @Param("status") StatusImovel status);

    @Query("SELECT i FROM Imovel i JOIN i.consultores c WHERE c = :consultor")
    List<Imovel> findByConsultores(@Param("consultor") Consultor consultor);

    @Query("SELECT i FROM Imovel i JOIN i.historicoConsultores hc WHERE hc = :consultor")
    List<Imovel> findByHistoricoConsultores(@Param("consultor") Consultor consultor);

    @Query("SELECT i FROM Imovel i JOIN i.comissoesConsultores cc WHERE cc = :consultor")
    List<Imovel> findByComissoesConsultores(@Param("consultor") Consultor consultor);

    @Query("SELECT i FROM Imovel i JOIN i.contatos c WHERE c.id = :contatoId")
    List<Imovel> findByContatos_Id(@Param("contatoId") Long contatoId);

    List<Imovel> findByStatus(StatusImovel status);

    List<Imovel> findByTipo(TipoImovel tipo);

    List<Imovel> findByCategoria(String categoria);

    @Query("SELECT i FROM Imovel i WHERE i.preco BETWEEN :precoMin AND :precoMax")
    List<Imovel> findByPrecoBetween(@Param("precoMin") Double precoMin, @Param("precoMax") Double precoMax);

    List<Imovel> findByAreaBetween(Double areaMin, Double areaMax);

    List<Imovel> findByQuartosGreaterThanEqual(Short quartos);

    @Query("SELECT i FROM Imovel i JOIN i.usuariosInteressados u WHERE u = :usuario")
    List<Imovel> findByUsuariosInteressados(@Param("usuario") Usuario usuario);

    @Query("SELECT i FROM Imovel i JOIN i.transacoes t WHERE t = :usuario")
    List<Imovel> findByTransacoes(@Param("usuario") Usuario usuario);

    @Query("SELECT i FROM Imovel i JOIN i.imoveisRelacionados ir WHERE ir.id = :imovelId")
    List<Imovel> findRelatedImoveis(@Param("imovelId") Long imovelId);

    @Query("SELECT i FROM Imovel i WHERE " +
            "(:status IS NULL OR i.status = :status) AND " +
            "(:tipo IS NULL OR i.tipo = :tipo) AND " +
            "(:precoMin IS NULL OR i.preco >= :precoMin) AND " +
            "(:precoMax IS NULL OR i.preco <= :precoMax) AND " +
            "(:categoria IS NULL OR i.categoria = :categoria) AND " +
            "(:areaMin IS NULL OR i.area >= :areaMin) AND " +
            "(:areaMax IS NULL OR i.area <= :areaMax) AND " +
            "(:quartos IS NULL OR i.quartos >= :quartos)")
    Page<Imovel> findByFiltrosOpcionais(
            @Param("status") StatusImovel status,
            @Param("tipo") TipoImovel tipo,
            @Param("precoMin") Double precoMin,
            @Param("precoMax") Double precoMax,
            @Param("categoria") String categoria,
            @Param("areaMin") Double areaMin,
            @Param("areaMax") Double areaMax,
            @Param("quartos") Short quartos,
            Pageable pageable
    );

    List<Imovel> findByEnderecoContainingIgnoreCase(String endereco);
}