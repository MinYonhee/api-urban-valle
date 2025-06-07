package com.realstate.imobiliaria.controller;

import com.realstate.imobiliaria.model.imoveis.Imovel;
import com.realstate.imobiliaria.model.imoveis.StatusImovel;
import com.realstate.imobiliaria.model.imoveis.TipoImovel;
import com.realstate.imobiliaria.model.usuarios.Usuario;
import com.realstate.imobiliaria.model.contato.Contato;
import com.realstate.imobiliaria.service.ImovelService;
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
@RequestMapping("/api/imoveis")
@CrossOrigin(origins = "https://seu-dominio.com")
public class ImovelController {

    private final ImovelService imovelService;

    @Autowired
    public ImovelController(ImovelService imovelService) {
        this.imovelService = imovelService;
    }

    @Operation(summary = "Lista todos os imóveis", description = "Retorna uma lista paginada de todos os imóveis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de imóveis retornada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<Page<Imovel>> listarTodos(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Imovel> imoveis = imovelService.listarTodos(pageable);
        return ResponseEntity.ok(imoveis);
    }

    @Operation(summary = "Cria um novo imóvel", description = "Adiciona um novo imóvel ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imóvel criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<Imovel> criarImovel(@RequestBody @Valid Imovel imovel) {
        Imovel novoImovel = imovelService.salvar(imovel);
        return new ResponseEntity<>(novoImovel, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca imóvel por ID", description = "Retorna um imóvel específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imóvel encontrado"),
            @ApiResponse(responseCode = "404", description = "Imóvel não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Imovel> buscarImovelPorId(@PathVariable Long id) {
        Imovel imovel = imovelService.buscarPorId(id);
        return ResponseEntity.ok(imovel);
    }

    @Operation(summary = "Atualiza um imóvel", description = "Atualiza um imóvel existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imóvel atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Imóvel não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Imovel> atualizarImovel(@PathVariable Long id, @RequestBody @Valid Imovel imovel) {
        Imovel imovelAtualizado = imovelService.atualizar(id, imovel);
        return ResponseEntity.ok(imovelAtualizado);
    }

    @Operation(summary = "Deleta um imóvel", description = "Remove um imóvel do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Imóvel deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Imóvel não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarImovel(@PathVariable Long id) {
        imovelService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Lista usuários interessados em um imóvel", description = "Retorna todos os usuários interessados em um imóvel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada"),
            @ApiResponse(responseCode = "404", description = "Imóvel não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{imovelId}/interessados")
    public ResponseEntity<List<Usuario>> listarInteressados(@PathVariable Long imovelId) {
        List<Usuario> interessados = imovelService.listarInteressados(imovelId);
        return ResponseEntity.ok(interessados);
    }

    @Operation(summary = "Lista contatos de um imóvel", description = "Retorna todos os contatos associados a um imóvel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de contatos retornada"),
            @ApiResponse(responseCode = "404", description = "Imóvel não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{imovelId}/contatos")
    public ResponseEntity<List<Contato>> listarContatos(@PathVariable Long imovelId) {
        List<Contato> contatos = imovelService.listarContatos(imovelId);
        return ResponseEntity.ok(contatos);
    }

    @Operation(summary = "Adiciona um contato a um imóvel", description = "Associa um novo contato a um imóvel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contato adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Imóvel não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/{imovelId}/contatos")
    public ResponseEntity<Contato> adicionarContato(@PathVariable Long imovelId, @RequestBody @Valid Contato contato) {
        Contato novoContato = imovelService.adicionarContato(imovelId, contato);
        return new ResponseEntity<>(novoContato, HttpStatus.CREATED);
    }

    @Operation(summary = "Filtra imóveis por critérios opcionais", description = "Retorna uma lista paginada de imóveis com base em filtros opcionais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de imóveis filtrada retornada"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/filtrar")
    public ResponseEntity<Page<Imovel>> filtrarImoveis(
            @RequestParam(required = false) StatusImovel status,
            @RequestParam(required = false) TipoImovel tipo,
            @RequestParam(required = false) Double precoMin,
            @RequestParam(required = false) Double precoMax,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Double areaMin,
            @RequestParam(required = false) Double areaMax,
            @RequestParam(required = false) Short quartos,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Imovel> imoveis = imovelService.filtrarImoveis(status, tipo, precoMin, precoMax, categoria, areaMin, areaMax, quartos, pageable);
        return ResponseEntity.ok(imoveis);
    }
}