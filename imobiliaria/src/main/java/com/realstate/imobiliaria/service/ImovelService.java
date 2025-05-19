package com.realstate.imobiliaria.service;

import com.realstate.imobiliaria.model.Imovel;
import com.realstate.imobiliaria.repository.ImovelRepository;
import com.realstate.imobiliaria.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImovelService {

    @Autowired
    private ImovelRepository imovelRepository;

    public List<Imovel> listarTodos() {
        return imovelRepository.findAll();
    }

    public Imovel buscarPorId(Long id) {
        return imovelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Imóvel com ID " + id + " não encontrado"));
    }

    public Imovel salvar(Imovel imovel) {
        return imovelRepository.save(imovel);
    }

    public Imovel atualizar(Long id, Imovel novoImovel) {
        return imovelRepository.findById(id).map(imovel -> {
            imovel.setTitulo(novoImovel.getTitulo());
            imovel.setDescricao(novoImovel.getDescricao());
            imovel.setPreco(novoImovel.getPreco());
            imovel.setEndereco(novoImovel.getEndereco());
            imovel.setTipo(novoImovel.getTipo());
            imovel.setQuartos(novoImovel.getQuartos());
            imovel.setBanheiros(novoImovel.getBanheiros());
            imovel.setArea(novoImovel.getArea());
            imovel.setStatus(novoImovel.getStatus());
            imovel.setImagemUrl(novoImovel.getImagemUrl());
            imovel.setCategoria(novoImovel.getCategoria());
            // dataCadastro não deve ser alterada
            return imovelRepository.save(imovel);
        }).orElseThrow(() -> new ResourceNotFoundException("Imóvel com ID " + id + " não encontrado"));
    }


    public void deletar(Long id) {
        if (!imovelRepository.existsById(id)) {
            throw new ResourceNotFoundException("Imóvel com ID " + id + " não encontrado");
        }
        imovelRepository.deleteById(id);
    }
}
