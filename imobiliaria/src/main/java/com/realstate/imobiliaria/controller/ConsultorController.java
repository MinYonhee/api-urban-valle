package com.realstate.imobiliaria.controller;

import com.realstate.imobiliaria.model.consultor.Consultor;
import com.realstate.imobiliaria.model.imoveis.Imovel;
import com.realstate.imobiliaria.service.ConsultorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultores")
@CrossOrigin(origins = "https://meu-dominio.com")
public class ConsultorController {

    private final ConsultorService consultorService;

    @Autowired
    public ConsultorController(ConsultorService consultorService) {

        this.consultorService = consultorService;
    }

    @Operation(summary = "Lista todos os consultores", description = "Retorna uma lista paginada de todos os consultores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de consultores retornada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<Page<Consultor>> listarTodos(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Consultor> consultores = consultorService.listarTodos(pageable);
        return ResponseEntity.ok(consultores);
    }

    @Operation(summary = "Cria um novo consultor", description = "Adiciona um novo consultor ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consultor criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<Consultor> criarConsultor(@RequestBody @Valid Consultor consultor) {
        Consultor novoConsultor = consultorService.salvar(consultor);
        return new ResponseEntity<>(novoConsultor, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca consultor por ID", description = "Retorna um consultor específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultor encontrado"),
            @ApiResponse(responseCode = "404", description = "Consultor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Consultor> buscarConsultorPorId(@PathVariable Long id) {
        Consultor consultor = consultorService.buscarPorId(id);
        return ResponseEntity.ok(consultor);
    }

    @Operation(summary = "Atualiza um consultor", description = "Atualiza um consultor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultor atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Consultor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Consultor> atualizarConsultor(@PathVariable Long id, @RequestBody @Valid Consultor consultor) {
        Consultor consultorAtualizado = consultorService.atualizar(id, consultor);
        return ResponseEntity.ok(consultorAtualizado);
    }

    @Operation(summary = "Deleta um consultor", description = "Remove um consultor do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Consultor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consultor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConsultor(@PathVariable Long id) {
        consultorService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Lista imóveis de um consultor", description = "Retorna todos os imóveis associados a um consultor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de imóveis retornada"),
            @ApiResponse(responseCode = "404", description = "Consultor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{consultorId}/imoveis")
    public ResponseEntity<List<Imovel>> listarImoveis(@PathVariable Long consultorId) {
        List<Imovel> imoveis = consultorService.listarImoveis(consultorId);
        return ResponseEntity.ok(imoveis);
    }

    @Operation(summary = "Lista imóveis no histórico de um consultor", description = "Retorna todos os imóveis no histórico de um consultor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de imóveis retornada"),
            @ApiResponse(responseCode = "404", description = "Consultor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{consultorId}/imoveis-historico")
    public ResponseEntity<List<Imovel>> listarImoveisHistorico(@PathVariable Long consultorId) {
        List<Imovel> imoveis = consultorService.listarImoveisHistorico(consultorId);
        return ResponseEntity.ok(imoveis);
    }

    @Operation(summary = "Lista imóveis com comissão de um consultor", description = "Retorna todos os imóveis associados a comissões de um consultor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de imóveis retornada"),
            @ApiResponse(responseCode = "404", description = "Consultor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{consultorId}/imoveis-comissao")
    public ResponseEntity<List<Imovel>> listarImoveisComissao(@PathVariable Long consultorId) {
        List<Imovel> imoveis = consultorService.listarImoveisComissao(consultorId);
        return ResponseEntity.ok(imoveis);
    }

    @Operation(summary = "Adiciona imóvel a um consultor", description = "Associa um imóvel a um consultor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imóvel adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Consultor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/{consultorId}/imoveis")
    public ResponseEntity<Consultor> adicionarImovel(@PathVariable Long consultorId, @RequestBody @Valid Imovel imovel) {
        Consultor consultor = consultorService.adicionarImovel(consultorId, imovel);
        return new ResponseEntity<>(consultor, HttpStatus.CREATED);
    }

    @Operation(summary = "Remove imóvel de um consultor", description = "Remove a associação de um imóvel com um consultor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Imóvel removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consultor ou imóvel não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{consultorId}/imoveis/{imovelId}")
    public ResponseEntity<Void> removerImovel(@PathVariable Long consultorId, @PathVariable Long imovelId) {
        Imovel imovel = new Imovel();
        imovel.setId(imovelId);
        consultorService.removerImovel(consultorId, imovel);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Adiciona imóvel ao histórico de um consultor", description = "Associa um imóvel ao histórico de um consultor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imóvel adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Consultor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/{consultorId}/imoveis-historico")
    public ResponseEntity<Consultor> adicionarImovelHistorico(@PathVariable Long consultorId, @RequestBody @Valid Imovel imovel) {
        Consultor consultor = consultorService.adicionarImovelHistorico(consultorId, imovel);
        return new ResponseEntity<>(consultor, HttpStatus.CREATED);
    }

    @Operation(summary = "Remove imóvel do histórico de um consultor", description = "Remove a associação de um imóvel do histórico de um consultor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Imóvel removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consultor ou imóvel não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{consultorId}/imoveis-historico/{imovelId}")
    public ResponseEntity<Void> removerImovelHistorico(@PathVariable Long consultorId, @PathVariable Long imovelId) {
        Imovel imovel = new Imovel();
        imovel.setId(imovelId);
        consultorService.removerImovelHistorico(consultorId, imovel);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Adiciona imóvel à comissão de um consultor", description = "Associa um imóvel à comissão de um consultor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imóvel adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Consultor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/{consultorId}/imoveis-comissao")
    public ResponseEntity<Consultor> adicionarImovelComissao(@PathVariable Long consultorId, @RequestBody @Valid Imovel imovel) {
        Consultor consultor = consultorService.adicionarImovelComissao(consultorId, imovel);
        return new ResponseEntity<>(consultor, HttpStatus.CREATED);
    }

    @Operation(summary = "Remove imóvel da comissão de um consultor", description = "Remove a associação de um imóvel da comissão de um consultor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Imóvel removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consultor ou imóvel não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{consultorId}/imoveis-comissao/{imovelId}")
    public ResponseEntity<Void> removerImovelComissao(@PathVariable Long consultorId, @PathVariable Long imovelId) {
        Imovel imovel = new Imovel();
        imovel.setId(imovelId);
        consultorService.removerImovelComissao(consultorId, imovel);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}