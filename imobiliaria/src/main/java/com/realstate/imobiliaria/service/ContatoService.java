package com.realstate.imobiliaria.service;

import com.realstate.imobiliaria.exception.ResourceNotFoundException;
import com.realstate.imobiliaria.model.Contato;
import com.realstate.imobiliaria.repository.ImovelRepository;
import com.realstate.imobiliaria.repository.UsuarioRepository;
import com.realstate.imobiliaria.repository.ConsultorRepository;
import com.realstate.imobiliaria.repository.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContatoService {

    private final ContatoRepository contatoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ImovelRepository imovelRepository;
    private final ConsultorRepository consultorRepository;

    @Autowired
    public ContatoService(ContatoRepository contatoRepository,
                          UsuarioRepository usuarioRepository,
                          ImovelRepository imovelRepository,
                          ConsultorRepository consultorRepository) {
        this.contatoRepository = contatoRepository;
        this.usuarioRepository = usuarioRepository;
        this.imovelRepository = imovelRepository;
        this.consultorRepository = consultorRepository;
    }

    public Contato salvar(Contato contato) {
        if (contato.getEmail() == null || contato.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (contato.getUsuario() != null && !usuarioRepository.existsById(contato.getUsuario().getId())) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        if (contato.getImovel() != null && !imovelRepository.existsById(contato.getImovel().getId())) {
            throw new IllegalArgumentException("Imóvel não encontrado");
        }
        if (contato.getConsultor() != null && !consultorRepository.existsById(contato.getConsultor().getId())) {
            throw new IllegalArgumentException("Consultor não encontrado");
        }
        contato.setData(LocalDateTime.now());
        if (contato.getStatus() == null) {
            contato.setStatus("PENDENTE");
        }
        return contatoRepository.save(contato);
    }

    public Contato buscarPorId(Long id) {
        return contatoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato com ID " + id + " não encontrado"));
    }

    public List<Contato> listarTodos() {
        return contatoRepository.findAll();
    }

    public Contato atualizar(Long id, Contato contatoAtualizado) {
        return contatoRepository.findById(id).map(contato -> {
            if (contatoAtualizado.getEmail() == null || contatoAtualizado.getEmail().isEmpty()) {
                throw new IllegalArgumentException("Email é obrigatório");
            }
            if (contatoAtualizado.getUsuario() != null &&
                    !usuarioRepository.existsById(contatoAtualizado.getUsuario().getId())) {
                throw new IllegalArgumentException("Usuário não encontrado");
            }
            if (contatoAtualizado.getImovel() != null &&
                    !imovelRepository.existsById(contatoAtualizado.getImovel().getId())) {
                throw new IllegalArgumentException("Imóvel não encontrado");
            }
            if (contatoAtualizado.getConsultor() != null &&
                    !consultorRepository.existsById(contatoAtualizado.getConsultor().getId())) {
                throw new IllegalArgumentException("Consultor não encontrado");
            }
            contato.setEmail(contatoAtualizado.getEmail());
            contato.setMensagem(contatoAtualizado.getMensagem());
            contato.setStatus(contatoAtualizado.getStatus());
            contato.setUsuario(contatoAtualizado.getUsuario());
            contato.setImovel(contatoAtualizado.getImovel());
            contato.setConsultor(contatoAtualizado.getConsultor());
            return contatoRepository.save(contato);
        }).orElseThrow(() -> new ResourceNotFoundException("Contato com ID " + id + " não encontrado"));
    }

    public void deletar(Long id) {
        if (!contatoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contato não encontrado com ID " + id);
        }
        contatoRepository.deleteById(id);
    }

    public List<Contato> listarPorConsultor(Long consultorId) {
        if (!consultorRepository.existsById(consultorId)) {
            throw new IllegalArgumentException("Consultor não encontrado");
        }
        return contatoRepository.findByConsultorId(consultorId);
    }
}