package com.eventosapi.demo.services;

import com.eventosapi.demo.dtos.FiltroUsuarioDTO;
import com.eventosapi.demo.dtos.UsuarioRequestDTO;
import com.eventosapi.demo.dtos.UsuarioResponseDTO;
import com.eventosapi.demo.enums.TipoUsuario;
import com.eventosapi.demo.models.Usuario;
import com.eventosapi.demo.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    private Usuario usuario;
    private UsuarioRequestDTO usuarioRequest;

    @BeforeEach
    void setup() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioService = new UsuarioService(usuarioRepository);

        
        usuario = Usuario.builder()
            .id(1L)
            .nome("José Vitor")
            .email("jose.vitor@example.com")
            .telefone("11999999999")
            .tipo(TipoUsuario.PARTICIPANTE)
            .build();


        usuarioRequest = new UsuarioRequestDTO(
                "José Vitor",
                "jose.vitor@example.com",
                "11999999999",
                TipoUsuario.PARTICIPANTE
        );
    }

    @Test
    void deveCadastrarUsuario() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioResponseDTO response = usuarioService.cadastrarUsuario(usuarioRequest);

        assertEquals("José Vitor", response.nome());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void deveObterUsuarioPorId() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        UsuarioResponseDTO response = usuarioService.obterUsuarioPorId(1L);

        assertEquals("José Vitor", response.nome());
        assertEquals("jose.vitor@example.com", response.email());
    }

    @Test
    void deveLancarExcecaoAoBuscarUsuarioInexistente() {
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> usuarioService.obterUsuarioPorId(999L));

        assertTrue(exception.getMessage().contains("Usuário não encontrado"));
    }

    @Test
    void deveDeletarUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.deletarUsuario(1L);

        verify(usuarioRepository, times(1)).delete(usuario);
    }

    @Test
    void deveAtualizarUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioResponseDTO response = usuarioService.atualizarUsuario(1L, usuarioRequest);

        assertEquals("José Vitor", response.nome());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void deveListarUsuarios() {
      FiltroUsuarioDTO filtroUsuarioDTO = new FiltroUsuarioDTO();
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Usuario> page = new PageImpl<>(List.of(usuario));
        when(usuarioRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<UsuarioResponseDTO> result = usuarioService.listarUsuarios(filtroUsuarioDTO, pageable);

        assertEquals(1, result.getTotalElements());
        verify(usuarioRepository).findAll(any(Specification.class), eq(pageable));
    }
}