package com.realstate.imobiliaria.service;

import com.realstate.imobiliaria.exception.ImovelNotFoundException;
import com.realstate.imobiliaria.exception.ImovelValidationException;
import com.realstate.imobiliaria.model.imoveis.Imovel;
import com.realstate.imobiliaria.model.usuarios.Usuario;
import com.realstate.imobiliaria.model.contato.Contato;
import com.realstate.imobiliaria.model.imoveis.StatusImovel;
import com.realstate.imobiliaria.model.imoveis.TipoImovel;
import com.realstate.imobiliaria.model.consultor.Consultor;
import com.realstate.imobiliaria.repository.ImovelRepository;
import com.realstate.imobiliaria.repository.UsuarioRepository;
import com.realstate.imobiliaria.repository.ConsultorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImovelService {

    private static final Logger logger = LoggerFactory.getLogger(ImovelService.class);

    private final ImovelRepository imovelRepository;
    private final UsuarioRepository usuarioRepository;
    private final ConsultorRepository consultorRepository;
    private final ContatoService contatoService;

    @Autowired
    public ImovelService(ImovelRepository imovelRepository,
                         UsuarioRepository usuarioRepository,
                         ConsultorRepository consultorRepository,
                         ContatoService contatoService) {
        this.imovelRepository = imovelRepository;
        this.usuarioRepository = usuarioRepository;
        this.consultorRepository = consultorRepository;
        this.contatoService = contatoService;
    }

    public Page<Imovel> listarTodos(Pageable pageable) {
        return imovelRepository.findAll(pageable);
    }

    public Imovel buscarPorId(Long id) {
        return imovelRepository.findById(id)
                .orElseThrow(() -> new ImovelNotFoundException(id));
    }

    public Imovel salvar(Imovel imovel) {
        logger.info("Tentando salvar imóvel: {}", imovel);
        validarImovel(imovel);
        Imovel salvo = imovelRepository.save(imovel);
        logger.info("Imóvel salvo com sucesso: {}", salvo);
        return salvo;
    }

    public Imovel atualizar(Long id, Imovel imovelAtualizado) {
        logger.info("Tentando atualizar imóvel ID: {}", id);
        return imovelRepository.findById(id).map(imovel -> {
            validarImovel(imovelAtualizado);
            imovel.setTitulo(imovelAtualizado.getTitulo());
            imovel.setDescricao(imovelAtualizado.getDescricao());
            imovel.setPreco(imovelAtualizado.getPreco());
            imovel.setEndereco(imovelAtualizado.getEndereco());
            imovel.setTipo(imovelAtualizado.getTipo());
            imovel.setQuartos(imovelAtualizado.getQuartos());
            imovel.setBanheiros(imovelAtualizado.getBanheiros());
            imovel.setArea(imovelAtualizado.getArea());
            imovel.setStatus(imovelAtualizado.getStatus());
            imovel.setImagemUrl(imovelAtualizado.getImagemUrl());
            imovel.setCategoria(imovelAtualizado.getCategoria());
            imovel.setProprietario(imovelAtualizado.getProprietario());
            imovel.setConsultores(imovelAtualizado.getConsultores());
            Imovel atualizado = imovelRepository.save(imovel);
            logger.info("Imóvel atualizado com sucesso: {}", atualizado);
            return atualizado;
        }).orElseThrow(() -> new ImovelNotFoundException(id));
    }

    public void deletar(Long id) {
        if (!imovelRepository.existsById(id)) {
            throw new ImovelNotFoundException(id);
        }
        imovelRepository.deleteById(id);
    }

    public List<Usuario> listarInteressados(Long imovelId) {
        Imovel imovel = buscarPorId(imovelId);
        return imovel.getUsuariosInteressados();
    }

    public List<Contato> listarContatos(Long imovelId) {
        Imovel imovel = buscarPorId(imovelId);
        return imovel.getContatos();
    }

    public Contato adicionarContato(Long imovelId, Contato contato) {
        logger.info("Tentando adicionar contato ao imóvel ID: {}", imovelId);
        Imovel imovel = buscarPorId(imovelId);
        contato.setImovel(imovel);
        Contato novoContato = contatoService.salvar(contato); // Usa ContatoService para salvar
        logger.info("Contato adicionado com sucesso: {}", novoContato);
        return novoContato;
    }

    public Page<Imovel> filtrarImoveis(
            StatusImovel status, TipoImovel tipo, Double precoMin, Double precoMax,
            String categoria, Double areaMin, Double areaMax, Short quartos, Pageable pageable) {
        return imovelRepository.findByFiltrosOpcionais(status, tipo, precoMin, precoMax, categoria, areaMin, areaMax, quartos, pageable);
    }

    private void validarImovel(Imovel imovel) {
        if (imovel.getProprietario() != null && imovel.getProprietario().getId() != null) {
            if (!usuarioRepository.existsById(imovel.getProprietario().getId())) {
                logger.error("Proprietário inválido: {}", imovel.getProprietario());
                throw new ImovelValidationException("Proprietário não encontrado");
            }
        }
        if (imovel.getConsultores() != null) {
            for (Consultor consultor : imovel.getConsultores()) {
                if (consultor.getId() == null || !consultorRepository.existsById(consultor.getId())) {
                    logger.error("Consultor inválido: {}", consultor);
                    throw new ImovelValidationException("Consultor não encontrado");
                }
            }
        }
    }
}