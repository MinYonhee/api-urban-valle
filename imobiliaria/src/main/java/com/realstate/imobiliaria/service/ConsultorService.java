package com.realstate.imobiliaria.service;

import com.realstate.imobiliaria.exception.ResourceNotFoundException;
import com.realstate.imobiliaria.model.Consultor;
import com.realstate.imobiliaria.repository.ConsultorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultorService {

    @Autowired
    private ConsultorRepository consultorRepository;

    public List<Consultor> listarTodos() {
        return consultorRepository.findAll();
    }

    public Consultor buscarPorId(Long id) {
        return consultorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultor com ID " + id + " não encontrado"));
    }

    public Consultor salvar(Consultor consultor) {
        return consultorRepository.save(consultor);
    }

    public Consultor atualizar(Long id, Consultor novoConsultor) {
        return consultorRepository.findById(id).map(consultor -> {
            consultor.setNome(novoConsultor.getNome());
            consultor.setEmail(novoConsultor.getEmail());
            consultor.setTelefone(novoConsultor.getTelefone());
            return consultorRepository.save(consultor);
        }).orElseThrow(() -> new ResourceNotFoundException("Consultor com ID " + id + " não encontrado"));
    }

    public void deletar(Long id) {
        if (!consultorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Consultor com ID " + id + " não encontrado");
        }
        consultorRepository.deleteById(id);
    }
}

