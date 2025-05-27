package com.realstate.imobiliaria.controller;

import com.realstate.imobiliaria.model.Contato;
import com.realstate.imobiliaria.service.ContatoService;
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
@RequestMapping("/api/contatos")
@CrossOrigin(origins = "*")
public class ContatoController {

    private final ContatoService contatoService;

    @Autowired
    public ContatoController(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    @Operation(summary = "Cria um novo contato", description = "Adiciona um novo contato ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contato criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou entidades relacionadas não encontradas"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<Contato> criarContato(@RequestBody @Valid Contato contato) {
        Contato novoContato = contatoService.salvar(contato);
        return new ResponseEntity<>(novoContato, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca contato por ID", description = "Retorna um contato específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contato encontrado"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Contato> buscarContatoPorId(@PathVariable Long id) {
        Contato contato = contatoService.buscarPorId(id);
        return ResponseEntity.ok(contato);
    }

    @Operation(summary = "Lista todos os contatos", description = "Retorna uma lista de todos os contatos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de contatos retornada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<List<Contato>> buscarTodosContatos() {
        List<Contato> contatos = contatoService.listarTodos();
        return ResponseEntity.ok(contatos);
    }

    @Operation(summary = "Atualiza um contato", description = "Atualiza um contato existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contato atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou entidades relacionadas não encontradas"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Contato> atualizarContato(@PathVariable Long id, @RequestBody @Valid Contato contato) {
        Contato contatoAtualizado = contatoService.atualizar(id, contato);
        return ResponseEntity.ok(contatoAtualizado);
    }

    @Operation(summary = "Deleta um contato", description = "Remove um contato do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Contato deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarContato(@PathVariable Long id) {
        contatoService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Lista contatos por consultor", description = "Retorna todos os contatos associados a um consultor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de contatos retornada"),
            @ApiResponse(responseCode = "404", description = "Consultor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/consultor/{consultorId}")
    public ResponseEntity<List<Contato>> listarPorConsultor(@PathVariable Long consultorId) {
        List<Contato> contatos = contatoService.listarPorConsultor(consultorId);
        return ResponseEntity.ok(contatos);
    }
}