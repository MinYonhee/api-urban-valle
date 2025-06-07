package com.realstate.imobiliaria.controller;

import com.realstate.imobiliaria.model.usuarios.Usuario;
import com.realstate.imobiliaria.model.imoveis.Imovel;
import com.realstate.imobiliaria.model.contato.Contato;
import com.realstate.imobiliaria.service.UsuarioService;
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

record LoginDTO(String email, String senha) {}

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "https://seu-dominio.com")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {

        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Lista todos os usuários", description = "Retorna uma lista de todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> usuariosPage = usuarioService.listarTodos(pageable);
        List<Usuario> usuarios = usuariosPage.getContent();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Cria um novo usuário", description = "Adiciona um novo usuário ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody @Valid Usuario usuario) {
        Usuario novoUsuario = usuarioService.salvar(usuario);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca usuário por ID", description = "Retorna um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Busca usuário por e-mail", description = "Retorna um usuário específico pelo e-mail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscarUsuarioPorEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.buscarPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Atualiza um usuário", description = "Atualiza um usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody @Valid Usuario usuario) {
        Usuario usuarioAtualizado = usuarioService.atualizar(id, usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @Operation(summary = "Deleta um usuário", description = "Remove um usuário do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Realiza login de usuário", description = "Autentica um usuário com e-mail e senha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody LoginDTO loginDTO) {
        Usuario usuario = usuarioService.login(loginDTO.email(), loginDTO.senha());
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Lista imóveis de um usuário", description = "Retorna todos os imóveis de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de imóveis retornada"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{usuarioId}/imoveis")
    public ResponseEntity<List<Imovel>> listarImoveis(@PathVariable Long usuarioId) {
        List<Imovel> imoveis = usuarioService.listarImoveis(usuarioId);
        return ResponseEntity.ok(imoveis);
    }

    @Operation(summary = "Lista imóveis que um usuário tem interesse", description = "Retorna todos os imóveis que um usuário está interessado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de imóveis retornada"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{usuarioId}/imoveis-interessados")
    public ResponseEntity<List<Imovel>> listarImoveisInteressados(@PathVariable Long usuarioId) {
        List<Imovel> imoveis = usuarioService.listarImoveisInteressados(usuarioId);
        return ResponseEntity.ok(imoveis);
    }

    @Operation(summary = "Lista imóveis transacionados de um usuário", description = "Retorna todos os imóveis transacionados de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de imóveis retornada"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{usuarioId}/imoveis-transacionados")
    public ResponseEntity<List<Imovel>> listarImoveisTransacionados(@PathVariable Long usuarioId) {
        List<Imovel> imoveis = usuarioService.listarImoveisTransacionados(usuarioId);
        return ResponseEntity.ok(imoveis);
    }

    @Operation(summary = "Lista contatos de um usuário", description = "Retorna todos os contatos de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de contatos retornada"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{usuarioId}/contatos")
    public ResponseEntity<List<Contato>> listarContatos(@PathVariable Long usuarioId) {
        List<Contato> contatos = usuarioService.listarContatos(usuarioId);
        return ResponseEntity.ok(contatos);
    }

    @Operation(summary = "Lista usuários por consultor", description = "Retorna todos os usuários associados a um consultor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada"),
            @ApiResponse(responseCode = "404", description = "Consultor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/consultor/{consultorId}")
    public ResponseEntity<List<Usuario>> listarPorConsultor(@PathVariable Long consultorId) {
        List<Usuario> usuarios = usuarioService.listarPorConsultor(consultorId);
        return ResponseEntity.ok(usuarios);
    }
}