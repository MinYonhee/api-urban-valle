package com.realstate.imobiliaria.service;

import com.realstate.imobiliaria.exception.CredenciaisInvalidasException;
import com.realstate.imobiliaria.exception.UsuarioNotFoundException;
import com.realstate.imobiliaria.exception.UsuarioValidationException;
import com.realstate.imobiliaria.model.usuarios.Usuario;
import com.realstate.imobiliaria.model.imoveis.Imovel;
import com.realstate.imobiliaria.model.contato.Contato;
import com.realstate.imobiliaria.model.consultor.Consultor;
import com.realstate.imobiliaria.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ConsultorService consultorService;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, ConsultorService consultorService) {
        this.usuarioRepository = usuarioRepository;
        this.consultorService = consultorService;
    }

    public Page<Usuario> listarTodos(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNotFoundException(email));
    }

    public Usuario salvar(Usuario usuario) {
        validarUsuario(usuario);
        if (usuario.getEmail() != null && usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new UsuarioValidationException("E-mail já cadastrado.");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario novoUsuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
        validarUsuario(novoUsuario);
        if (novoUsuario.getEmail() != null &&
                !novoUsuario.getEmail().equals(usuarioExistente.getEmail()) &&
                usuarioRepository.findByEmail(novoUsuario.getEmail()).isPresent()) {
            throw new UsuarioValidationException("E-mail já cadastrado.");
        }
        usuarioExistente.setNome(novoUsuario.getNome());
        usuarioExistente.setEmail(novoUsuario.getEmail());
        usuarioExistente.setSenha(novoUsuario.getSenha());
        usuarioExistente.setTelefone(novoUsuario.getTelefone());
        return usuarioRepository.save(usuarioExistente);
    }

    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNotFoundException(id);
        }
        usuarioRepository.deleteById(id);
    }

    public Usuario login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new CredenciaisInvalidasException("E-mail não encontrado"));
        if (!senha.equals(usuario.getSenha())) {
            throw new CredenciaisInvalidasException("Senha incorreta");
        }
        return usuario;
    }

    public List<Imovel> listarImoveis(Long usuarioId) {
        Usuario usuario = buscarPorId(usuarioId);
        return usuario.getImoveis();
    }

    public List<Imovel> listarImoveisInteressados(Long usuarioId) {
        Usuario usuario = buscarPorId(usuarioId);
        return usuario.getImoveisInteressados();
    }

    public List<Imovel> listarImoveisTransacionados(Long usuarioId) {
        Usuario usuario = buscarPorId(usuarioId);
        return usuario.getImoveisTransacionados();
    }

    public List<Contato> listarContatos(Long usuarioId) {
        Usuario usuario = buscarPorId(usuarioId);
        return usuario.getContatos();
    }

    public List<Usuario> listarPorConsultor(Long consultorId) {
        Consultor consultor = consultorService.buscarPorId(consultorId);
        return usuarioRepository.findByConsultorId(consultorId);
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new UsuarioValidationException("E-mail é obrigatório.");
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            throw new UsuarioValidationException("Senha é obrigatória.");
        }
        if (usuario.getSenha() != null && usuario.getSenha().length() < 6) {
            throw new UsuarioValidationException("Senha deve ter no mínimo 6 caracteres.");
        }
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new UsuarioValidationException("Nome é obrigatório.");
        }
        if (usuario.getTelefone() != null && !usuario.getTelefone().matches("\\d{10,11}")) {
            throw new UsuarioValidationException("Telefone deve conter 10 ou 11 dígitos.");
        }
    }
}