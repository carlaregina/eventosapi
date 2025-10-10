package com.eventosapi.application.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eventosapi.application.exceptions.DuplicidadeEmailUsuarioException;
import com.eventosapi.application.exceptions.EntidadeNaoEncontradoException;
import com.eventosapi.application.port.SenhaEncoderPort;
import com.eventosapi.application.port.UsuarioRepositoryPort;
import com.eventosapi.domain.models.Usuario;
import com.eventosapi.interfaces.dtos.FiltroUsuarioDTO;

@Service
public class UsuarioService {
    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final SenhaEncoderPort senhaEncodePort;

    public UsuarioService(UsuarioRepositoryPort usuarioRepositoryPort, SenhaEncoderPort senhaEncodePort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.senhaEncodePort = senhaEncodePort;
    }

    public Usuario cadastrarUsuario(Usuario usuario){
        if(usuarioRepositoryPort.existeEmail(usuario.getEmail())){
            throw new DuplicidadeEmailUsuarioException("Já existe um usuário cadastrado com o email: " + usuario.getEmail());
        }
        usuario.setSenha(senhaEncodePort.encode(usuario.getSenha()));

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

}