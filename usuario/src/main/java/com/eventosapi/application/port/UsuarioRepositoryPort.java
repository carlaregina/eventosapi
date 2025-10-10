package com.eventosapi.application.port;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eventosapi.domain.models.Usuario;
import com.eventosapi.interfaces.dtos.FiltroUsuarioDTO;

public interface UsuarioRepositoryPort {
    Boolean existeEmail(String email);
    Usuario salvar(Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
    void deletar(Long id);
    Page<Usuario> buscarTodos(FiltroUsuarioDTO filtro, Pageable pageable);
}