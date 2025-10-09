package com.eventosapi.controllers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import com.eventosapi.application.services.UsuarioService;
import com.eventosapi.domain.enums.TipoUsuario;
import com.eventosapi.domain.models.Usuario;
import com.eventosapi.interfaces.controllers.UsuarioController;
import com.eventosapi.interfaces.dtos.FiltroUsuarioDTO;
import com.eventosapi.interfaces.dtos.UsuarioRequestDTO;
import com.eventosapi.interfaces.dtos.UsuarioResponseDTO;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;
    private UsuarioRequestDTO usuarioRequestDTO;
    private UsuarioResponseDTO usuarioResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = Usuario.builder()
                .id(1L)
                .nome("José Vitor")
                .email("jose.vitor@example.com")
                .telefone("11999999999")
                .senha("senhaSegura123")
                .tipo(TipoUsuario.STAFF)
                .build();

        usuarioRequestDTO = new UsuarioRequestDTO(
                "José Vitor",
                "jose.vitor@example.com",
                "senhaSegura123",
                "11999999999",
                TipoUsuario.STAFF
        );

        usuarioResponseDTO = new UsuarioResponseDTO(
                "José Vitor",
                "jose.vitor@example.com",
                "11999999999",
                TipoUsuario.STAFF
        );
    }

    @Test
    void deveObterUsuarioPorId() {
        when(usuarioService.obterUsuarioPorId(1L)).thenReturn(usuario);

        ResponseEntity<UsuarioResponseDTO> response = usuarioController.obterPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("José Vitor", response.getBody().nome());
    }

    @Test
    void deveCadastrarUsuario() {
        when(usuarioService.cadastrarUsuario(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<UsuarioResponseDTO> response = usuarioController.cadastrar(usuarioRequestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("José Vitor", response.getBody().nome());
    }

    @Test
    void deveAtualizarUsuario() {
        when(usuarioService.atualizarUsuario(eq(1L), any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<UsuarioResponseDTO> response = usuarioController.atualizar(1L, usuarioRequestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("José Vitor", response.getBody().nome());
    }

    @Test
    void deveDeletarUsuario() {
        doNothing().when(usuarioService).deletarUsuario(1L);

        ResponseEntity<Void> response = usuarioController.deletar(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(usuarioService, times(1)).deletarUsuario(1L);
    }

    @Test
    void deveBuscarTodosUsuarios() {
        FiltroUsuarioDTO filtro = new FiltroUsuarioDTO("José Vitor", "jose.vitor@example.com", "11999999999", TipoUsuario.STAFF);
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Usuario> page = new PageImpl<>(List.of(usuario));

        when(usuarioService.buscarTodosUsuarios(any(FiltroUsuarioDTO.class), any(PageRequest.class))).thenReturn(page);

        Page<UsuarioResponseDTO> response = usuarioController.buscarTodosUsuarios(filtro, pageable);

        assertEquals(1, response.getTotalElements());
        assertEquals("José Vitor", response.getContent().get(0).nome());
    }
}