package com.realstate.imobiliaria.repository;

import com.realstate.imobiliaria.model.contato.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {
    List<Contato> findByUsuarioId(Long usuarioId);
    List<Contato> findByImovelId(Long imovelId);
    List<Contato> findByConsultorId(Long consultorId);
}