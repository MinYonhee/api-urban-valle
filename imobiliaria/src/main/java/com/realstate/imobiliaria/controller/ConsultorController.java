package com.realstate.imobiliaria.controller;

import com.realstate.imobiliaria.model.Consultor;
import com.realstate.imobiliaria.service.ConsultorService;
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
@RequestMapping("/api/consultores")
@CrossOrigin(origins = "*")
public class ConsultorController {

    @Autowired
    private ConsultorService consultorService;

    @Operation(summary = "Lista todos os consultores", description = "Retorna uma lista de todos os consultores cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de consultores retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<List<Consultor>> listarTodos() {
        List<Consultor> consultores = consultorService.listarTodos();
        return ResponseEntity.ok(consultores);
    }

    @Operation(summary = "Busca consultor por ID", description = "Retorna os detalhes de um consultor específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultor encontrado"),
            @ApiResponse(responseCode = "404", description = "Consultor não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Consultor> buscarPorId(@PathVariable Long id) {
        Consultor consultor = consultorService.buscarPorId(id);
        return ResponseEntity.ok(consultor);
    }

    @Operation(summary = "Cria um novo consultor", description = "Adiciona um novo consultor ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consultor criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<Consultor> criar(@RequestBody @Valid Consultor consultor) {
        Consultor novoConsultor = consultorService.salvar(consultor);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoConsultor);
    }

    @Operation(summary = "Atualiza um consultor", description = "Atualiza os dados de um consultor existente com base no ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consultor não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Consultor> atualizar(@PathVariable Long id, @RequestBody @Valid Consultor consultor) {
        Consultor consultorAtualizado = consultorService.atualizar(id, consultor);
        return ResponseEntity.ok(consultorAtualizado);
    }

    @Operation(summary = "Deleta um consultor", description = "Remove um consultor do sistema com base no ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Consultor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consultor não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        consultorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

