package com.eventosapi.services;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.eventosapi.application.port.UsuarioRepositoryPort;
import com.eventosapi.application.services.UsuarioService;
import com.eventosapi.domain.enums.TipoUsuario;
import com.eventosapi.domain.models.Usuario;
import com.eventosapi.interfaces.dtos.FiltroUsuarioDTO;

class UsuarioServiceTest {

    private UsuarioRepositoryPort usuarioRepository;
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setup() {
        usuarioRepository = mock(UsuarioRepositoryPort.class);
        usuarioService = new UsuarioService(usuarioRepository);
        
        usuario = Usuario.builder()
            .id(1L)
            .nome("José Vitor")
            .email("jose.vitor@example.com")
            .telefone("11999999999")
            .tipo(TipoUsuario.PARTICIPANTE)
            .build();
    }

    @Test
    void deveCadastrarUsuario() {
        when(usuarioRepository.salvar(any(Usuario.class))).thenReturn(usuario);

        Usuario response = usuarioService.cadastrarUsuario(usuario);

        assertEquals("José Vitor", response.getNome());
        verify(usuarioRepository, times(1)).salvar(any(Usuario.class));
    }

    @Test
    void deveObterUsuarioPorId() {
        when(usuarioRepository.buscarPorId(1L)).thenReturn(Optional.of(usuario));

        Usuario response = usuarioService.obterUsuarioPorId(1L);

        assertEquals("José Vitor", response.getNome());
        assertEquals("jose.vitor@example.com", response.getEmail());
    }

    @Test
    void deveLancarExcecaoAoBuscarUsuarioInexistente() {
        when(usuarioRepository.buscarPorId(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> usuarioService.obterUsuarioPorId(999L));

        assertTrue(exception.getMessage().contains("Usuário não encontrado"));
    }

    @Test
    void deveDeletarUsuario() {
        when(usuarioRepository.buscarPorId(1L)).thenReturn(Optional.of(usuario));

        usuarioService.deletarUsuario(1L);

        verify(usuarioRepository, times(1)).deletar(usuario.getId());
    }

    @Test
    void deveAtualizarUsuario() {
        when(usuarioRepository.buscarPorId(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.salvar(any(Usuario.class))).thenReturn(usuario);

        Usuario response = usuarioService.atualizarUsuario(1L, usuario);

        assertEquals("José Vitor", response.getNome());
        verify(usuarioRepository, times(1)).salvar(usuario);
    }

    @Test
    void deveListarUsuarios() {
        FiltroUsuarioDTO filtroUsuarioDTO = new FiltroUsuarioDTO("José Vitor", "jose.vitor@example.com", "11999999999", TipoUsuario.PARTICIPANTE);
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Usuario> page = new PageImpl<>(List.of(usuario));

        // Mock do método que deve ser chamado dentro do service
        when(usuarioRepository.buscarTodos(any(FiltroUsuarioDTO.class), any(Pageable.class))).thenReturn(page);

        Page<Usuario> result = usuarioService.buscarTodosUsuarios(filtroUsuarioDTO, pageable);

        assertEquals(1, result.getTotalElements());
    }
}