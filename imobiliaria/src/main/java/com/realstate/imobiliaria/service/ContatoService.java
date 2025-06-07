package com.realstate.imobiliaria.service;

import com.realstate.imobiliaria.exception.ContatoNotFoundException;
import com.realstate.imobiliaria.exception.ContatoValidationException;
import com.realstate.imobiliaria.model.contato.StatusContato;
import com.realstate.imobiliaria.model.contato.Contato;
import com.realstate.imobiliaria.repository.ContatoRepository;
import com.realstate.imobiliaria.repository.ImovelRepository;
import com.realstate.imobiliaria.repository.UsuarioRepository;
import com.realstate.imobiliaria.repository.ConsultorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContatoService {

    private static final Logger logger = LoggerFactory.getLogger(ContatoService.class);

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

    public Page<Contato> listarTodos(Pageable pageable) {
        return contatoRepository.findAll(pageable);
    }

    public Contato buscarPorId(Long id) {
        return contatoRepository.findById(id)
                .orElseThrow(() -> new ContatoNotFoundException(id));
    }

    public Contato salvar(Contato contato) {
        logger.info("Tentando salvar contato: {}", contato);
        if (contato.getUsuario() != null) {
            if (contato.getUsuario().getId() == null || !usuarioRepository.existsById(contato.getUsuario().getId())) {
                logger.error("Usuário inválido: {}", contato.getUsuario());
                throw new ContatoValidationException("Usuário não encontrado");
            }
        }
        if (contato.getImovel() != null) {
            if (contato.getImovel().getId() == null || !imovelRepository.existsById(contato.getImovel().getId())) {
                logger.error("Imóvel inválido: {}", contato.getImovel());
                throw new ContatoValidationException("Imóvel não encontrado");
            }
        }
        if (contato.getConsultor() != null) {
            if (contato.getConsultor().getId() == null || !consultorRepository.existsById(contato.getConsultor().getId())) {
                logger.error("Consultor inválido: {}", contato.getConsultor());
                throw new ContatoValidationException("Consultor não encontrado");
            }
        }
        contato.setData(LocalDateTime.now());
        if (contato.getStatus() == null) {
            contato.setStatus(StatusContato.PENDENTE);
        }
        Contato salvo = contatoRepository.save(contato);
        logger.info("Contato salvo com sucesso: {}", salvo);
        return salvo;
    }

    public Contato atualizar(Long id, Contato contatoAtualizado) {
        logger.info("Tentando atualizar contato ID: {}", id);
        return contatoRepository.findById(id).map(contato -> {
            if (contatoAtualizado.getUsuario() != null &&
                    (contatoAtualizado.getUsuario().getId() == null ||
                            !usuarioRepository.existsById(contatoAtualizado.getUsuario().getId()))) {
                logger.error("Usuário inválido: {}", contatoAtualizado.getUsuario());
                throw new ContatoValidationException("Usuário não encontrado");
            }
            if (contatoAtualizado.getImovel() != null &&
                    (contatoAtualizado.getImovel().getId() == null ||
                            !imovelRepository.existsById(contatoAtualizado.getImovel().getId()))) {
                logger.error("Imóvel inválido: {}", contatoAtualizado.getImovel());
                throw new ContatoValidationException("Imóvel não encontrado");
            }
            if (contatoAtualizado.getConsultor() != null &&
                    (contatoAtualizado.getConsultor().getId() == null ||
                            !consultorRepository.existsById(contatoAtualizado.getConsultor().getId()))) {
                logger.error("Consultor inválido: {}", contatoAtualizado.getConsultor());
                throw new ContatoValidationException("Consultor não encontrado");
            }
            contato.setEmail(contatoAtualizado.getEmail());
            contato.setMensagem(contatoAtualizado.getMensagem());
            contato.setStatus(contatoAtualizado.getStatus() != null ? contatoAtualizado.getStatus() : StatusContato.PENDENTE);
            contato.setUsuario(contatoAtualizado.getUsuario());
            contato.setImovel(contatoAtualizado.getImovel());
            contato.setConsultor(contatoAtualizado.getConsultor());
            Contato atualizado = contatoRepository.save(contato);
            logger.info("Contato atualizado com sucesso: {}", atualizado);
            return atualizado;
        }).orElseThrow(() -> new ContatoNotFoundException(id));
    }

    public void deletar(Long id) {
        if (!contatoRepository.existsById(id)) {
            throw new ContatoNotFoundException(id);
        }
        contatoRepository.deleteById(id);
    }

    public List<Contato> listarPorUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ContatoValidationException("Usuário não encontrado");
        }
        return contatoRepository.findByUsuarioId(usuarioId);
    }

    public List<Contato> listarPorImovel(Long imovelId) {
        if (!imovelRepository.existsById(imovelId)) {
            throw new ContatoValidationException("Imóvel não encontrado");
        }
        return contatoRepository.findByImovelId(imovelId);
    }

    public List<Contato> listarPorConsultor(Long consultorId) {
        if (!consultorRepository.existsById(consultorId)) {
            throw new ContatoValidationException("Consultor não encontrado");
        }
        return contatoRepository.findByConsultorId(consultorId);
    }
}