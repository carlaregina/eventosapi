package com.eventosapi.application.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eventosapi.application.exceptions.DuplicidadeEmailUsuarioException;
import com.eventosapi.application.exceptions.EntidadeNaoEncontradoException;
import com.eventosapi.application.port.UsuarioRepositoryPort;
import com.eventosapi.domain.models.Usuario;
import com.eventosapi.interfaces.dtos.FiltroUsuarioDTO;
import com.eventosapi.interfaces.dtos.UsuarioRequestDTO;
import com.eventosapi.interfaces.dtos.UsuarioResponseDTO;

@Service
public class UsuarioService {
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public UsuarioService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    public Usuario cadastrarUsuario(Usuario usuario){
        if(usuarioRepositoryPort.existeEmail(usuario.getEmail())){
            throw new DuplicidadeEmailUsuarioException("Já existe um usuário cadastrado com o email: " + usuario.getEmail());
        }
        return usuarioRepositoryPort.salvar(usuario);
    }

    public Usuario obterUsuarioPorId(Long id){
        return usuarioRepositoryPort.buscarPorId(id)
            .orElseThrow(() -> new EntidadeNaoEncontradoException("Usuário não encontrado com ID: " + id));
    }

    public void deletarUsuario(Long id){
        if(!usuarioRepositoryPort.buscarPorId(id).isPresent()){
            throw new EntidadeNaoEncontradoException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepositoryPort.deletar(id);
    }

    public Usuario atualizarUsuario(Long id, Usuario usuario){
        Usuario usuarioExistente = usuarioRepositoryPort.buscarPorId(id)
            .orElseThrow(() -> new EntidadeNaoEncontradoException("Usuário não encontrado com ID: " + id));

        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setTelefone(usuario.getTelefone());
        usuarioExistente.setTipo(usuario.getTipo());

        return usuarioRepositoryPort.salvar(usuarioExistente);
    }

    public Page<Usuario> buscarTodosUsuarios(FiltroUsuarioDTO filtroUsuarioDTO, Pageable pageable){
        return usuarioRepositoryPort.buscarTodos(filtroUsuarioDTO, pageable);
    }

    public static UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getTelefone(),
            usuario.getTipo()
        );
    }

    public static Usuario fromRequestDTO(UsuarioRequestDTO dto) {
        return Usuario.builder()
            .nome(dto.nome())
            .email(dto.email())
            .telefone(dto.telefone())
            .tipo(dto.tipo())
            .build();
    }
}