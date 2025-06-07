package com.realstate.imobiliaria.service;

import com.realstate.imobiliaria.exception.ConsultorNotFoundException;
import com.realstate.imobiliaria.exception.ConsultorValidationException;
import com.realstate.imobiliaria.model.consultor.Consultor;
import com.realstate.imobiliaria.model.imoveis.Imovel;
import com.realstate.imobiliaria.repository.ConsultorRepository;
import com.realstate.imobiliaria.repository.ImovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultorService {

    @Autowired
    private ConsultorRepository consultorRepository;

    @Autowired
    private ImovelRepository imovelRepository;

    public Page<Consultor> listarTodos(Pageable pageable) {
        return consultorRepository.findAll(pageable);
    }

    public Consultor buscarPorId(Long id) {
        return consultorRepository.findById(id)
                .orElseThrow(() -> new ConsultorNotFoundException(id));
    }

    public Consultor buscarPorEmail(String email) {
        return consultorRepository.findByEmail(email)
                .orElseThrow(() -> new ConsultorNotFoundException("Consultor com e-mail " + email + " não encontrado."));
    }

    public Consultor salvar(Consultor consultor) {
        validarConsultor(consultor);
        if (consultor.getEmail() != null && consultorRepository.findByEmail(consultor.getEmail()).isPresent()) {
            throw new ConsultorValidationException("E-mail já cadastrado.");
        }
        return consultorRepository.save(consultor);
    }

    public Consultor atualizar(Long id, Consultor novoConsultor) {
        Consultor consultorExistente = consultorRepository.findById(id)
                .orElseThrow(() -> new ConsultorNotFoundException(id));
        validarConsultor(novoConsultor);
        // Verifica e-mail duplicado, ignorando o próprio consultor
        if (novoConsultor.getEmail() != null &&
                !novoConsultor.getEmail().equals(consultorExistente.getEmail()) &&
                consultorRepository.findByEmail(novoConsultor.getEmail()).isPresent()) {
            throw new ConsultorValidationException("E-mail já cadastrado.");
        }
        consultorExistente.setNome(novoConsultor.getNome());
        consultorExistente.setEmail(novoConsultor.getEmail());
        consultorExistente.setTelefone(novoConsultor.getTelefone());
        return consultorRepository.save(consultorExistente);
    }

    public void deletar(Long id) {
        if (!consultorRepository.existsById(id)) {
            throw new ConsultorNotFoundException(id);
        }
        consultorRepository.deleteById(id);
    }

    public List<Imovel> listarImoveis(Long consultorId) {
        Consultor consultor = buscarPorId(consultorId);
        return consultor.getImoveis();
    }

    public List<Imovel> listarImoveisHistorico(Long consultorId) {
        Consultor consultor = buscarPorId(consultorId);
        return consultor.getImoveisHistorico();
    }

    public List<Imovel> listarImoveisComissao(Long consultorId) {
        Consultor consultor = buscarPorId(consultorId);
        return consultor.getImoveisComissao();
    }

    public Consultor adicionarImovel(Long consultorId, Imovel imovel) {
        Consultor consultor = buscarPorId(consultorId);
        if (imovel.getId() == null || !imovelRepository.existsById(imovel.getId())) {
            throw new ConsultorValidationException("Imóvel inválido ou não encontrado.");
        }
        consultor.addImovel(imovel);
        return consultorRepository.save(consultor);
    }

    public Consultor removerImovel(Long consultorId, Imovel imovel) {
        Consultor consultor = buscarPorId(consultorId);
        if (imovel.getId() == null || !imovelRepository.existsById(imovel.getId())) {
            throw new ConsultorValidationException("Imóvel inválido ou não encontrado.");
        }
        consultor.removeImovel(imovel);
        return consultorRepository.save(consultor);
    }

    public Consultor adicionarImovelHistorico(Long consultorId, Imovel imovel) {
        Consultor consultor = buscarPorId(consultorId);
        if (imovel.getId() == null || !imovelRepository.existsById(imovel.getId())) {
            throw new ConsultorValidationException("Imóvel inválido ou não encontrado.");
        }
        consultor.addImovelHistorico(imovel);
        return consultorRepository.save(consultor);
    }

    public Consultor removerImovelHistorico(Long consultorId, Imovel imovel) {
        Consultor consultor = buscarPorId(consultorId);
        if (imovel.getId() == null || !imovelRepository.existsById(imovel.getId())) {
            throw new ConsultorValidationException("Imóvel inválido ou não encontrado.");
        }
        consultor.removeImovelHistorico(imovel);
        return consultorRepository.save(consultor);
    }

    public Consultor adicionarImovelComissao(Long consultorId, Imovel imovel) {
        Consultor consultor = buscarPorId(consultorId);
        if (imovel.getId() == null || !imovelRepository.existsById(imovel.getId())) {
            throw new ConsultorValidationException("Imóvel inválido ou não encontrado.");
        }
        consultor.addImovelComissao(imovel);
        return consultorRepository.save(consultor);
    }

    public Consultor removerImovelComissao(Long consultorId, Imovel imovel) {
        Consultor consultor = buscarPorId(consultorId);
        if (imovel.getId() == null || !imovelRepository.existsById(imovel.getId())) {
            throw new ConsultorValidationException("Imóvel inválido ou não encontrado.");
        }
        consultor.removeImovelComissao(imovel);
        return consultorRepository.save(consultor);
    }

    private void validarConsultor(Consultor consultor) {
        if (consultor.getNome() == null || consultor.getNome().trim().isEmpty()) {
            throw new ConsultorValidationException("Nome é obrigatório.");
        }
        if (!consultor.getNome().matches("^[A-Za-zÀ-ÿ\\s]+$")) { // Reforça validação no service
            throw new ConsultorValidationException("Nome não pode conter números.");
        }
        if (consultor.getEmail() == null || consultor.getEmail().trim().isEmpty()) {
            throw new ConsultorValidationException("E-mail é obrigatório.");
        }
        if (consultor.getTelefone() != null && !consultor.getTelefone().matches("\\d{10,11}")) {
            throw new ConsultorValidationException("Telefone deve conter 10 ou 11 dígitos.");
        }
    }
}