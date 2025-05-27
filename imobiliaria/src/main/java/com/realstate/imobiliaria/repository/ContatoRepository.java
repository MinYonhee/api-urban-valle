package com.realstate.imobiliaria.repository;

import com.realstate.imobiliaria.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {
    List<Contato> findByConsultorId(Long consultorId);
}