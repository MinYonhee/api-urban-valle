package com.realstate.imobiliaria.controller;

import com.realstate.imobiliaria.model.Imovel;
import com.realstate.imobiliaria.service.ImovelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/imoveis")
@CrossOrigin(origins = "*")
public class ImovelController {

    @Autowired
    private ImovelService imovelService;

    @Operation(summary = "Lista todos os imóveis", description = "Retorna uma lista de todos os imóveis cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de imóveis retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<List<Imovel>> listarTodos() {
        List<Imovel> imoveis = imovelService.listarTodos();
        return ResponseEntity.ok(imoveis);
    }

    @Operation(summary = "Busca imóvel por ID", description = "Retorna os detalhes de um imóvel específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imóvel encontrado"),
            @ApiResponse(responseCode = "404", description = "Imóvel não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Imovel> buscarPorId(@PathVariable Long id) {
        Imovel imovel = imovelService.buscarPorId(id);
        return ResponseEntity.ok(imovel);
    }

    @Operation(summary = "Cria um novo imóvel", description = "Adiciona um novo imóvel ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imóvel criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<Imovel> criar(@RequestBody @Valid Imovel imovel) {
        Imovel novoImovel = imovelService.salvar(imovel);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoImovel);
    }

    @Operation(summary = "Atualiza um imóvel", description = "Atualiza os dados de um imóvel existente com base no ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imóvel atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Imóvel não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Imovel> atualizar(@PathVariable Long id, @RequestBody @Valid Imovel imovel) {
        Imovel imovelAtualizado = imovelService.atualizar(id, imovel);
        return ResponseEntity.ok(imovelAtualizado);
    }

    @Operation(summary = "Deleta um imóvel", description = "Remove um imóvel do sistema com base no ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Imóvel deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Imóvel não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        imovelService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}