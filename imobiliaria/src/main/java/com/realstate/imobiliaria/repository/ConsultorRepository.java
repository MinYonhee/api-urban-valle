package com.realstate.imobiliaria.repository;

import com.realstate.imobiliaria.model.Consultor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultorRepository extends JpaRepository<Consultor, Long> {
}


