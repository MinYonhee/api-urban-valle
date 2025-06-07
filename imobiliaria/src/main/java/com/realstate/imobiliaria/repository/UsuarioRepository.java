package com.realstate.imobiliaria.repository;

import com.realstate.imobiliaria.model.usuarios.Usuario;
import com.realstate.imobiliaria.model.imoveis.Imovel;
import com.realstate.imobiliaria.model.consultor.Consultor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario u JOIN u.imoveis i WHERE i.id = :imovelId")
    List<Usuario> findByImoveis(@Param("imovelId") Long imovelId);

    @Query("SELECT u FROM Usuario u JOIN u.imoveisInteressados i WHERE i.id = :imovelId")
    List<Usuario> findInteressadosByImovelId(@Param("imovelId") Long imovelId);

    @Query("SELECT u FROM Usuario u JOIN u.imoveisTransacionados t WHERE t.id = :imovelId")
    List<Usuario> findTransacionadosByImovelId(@Param("imovelId") Long imovelId);

    @Query("SELECT u FROM Usuario u JOIN u.contatos c WHERE c.id = :contatoId")
    List<Usuario> findByContatos(@Param("contatoId") Long contatoId);

    @Query("SELECT u FROM Usuario u JOIN u.consultor c WHERE c.id = :consultorId")
    List<Usuario> findByConsultorId(@Param("consultorId") Long consultorId);
}