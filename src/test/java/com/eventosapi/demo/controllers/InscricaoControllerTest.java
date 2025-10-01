package com.eventosapi.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import com.eventosapi.demo.controller.EventoController;
import com.eventosapi.demo.dtos.EventoRequestDTO;
import com.eventosapi.demo.dtos.EventoResponseDTO;
import com.eventosapi.demo.dtos.FiltroEventoDTO;
import com.eventosapi.demo.dtos.UsuarioResponseDTO;
import com.eventosapi.demo.enums.TipoEvento;
import com.eventosapi.demo.enums.TipoUsuario;
import com.eventosapi.demo.services.EventoService;
import com.eventosapi.demo.controller.InscricaoController;
import com.eventosapi.demo.dtos.FiltroInscricaoDTO;
import com.eventosapi.demo.dtos.InscricaoRequestDTO;
import com.eventosapi.demo.dtos.InscricaoResponseDTO;
import com.eventosapi.demo.services.InscricaoService;    
import com.eventosapi.demo.enums.StatusInscricao;
import com.eventosapi.demo.models.Inscricao;
import com.eventosapi.demo.models.Evento;
import com.eventosapi.demo.models.Usuario;  
import java.time.LocalDateTime;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;  






@WebMvcTest(controllers = InscricaoController.class)
@AutoConfigureMockMvc
class InscricaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InscricaoService inscricaoService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test

    void deveCriarInscricaoComSucesso() throws Exception {
        InscricaoRequestDTO request = new InscricaoRequestDTO(
            null, 1L, 2L, StatusInscricao.CONFIRMADA
        );
       
    Evento evento = Evento.builder()
        .id(1L)
        .titulo("Workshop Java")
        .build();

    Usuario usuario = Usuario.builder()
        .id(2L)
        .nome("Carla")
        .build();

    Inscricao inscricao = Inscricao.builder()
        .id(10L)
        .evento(evento)
        .usuario(usuario)
        .data(LocalDateTime.now())
        .status(StatusInscricao.CONFIRMADA)
        .build();

    when(inscricaoService.criar(any())).thenReturn(inscricao);

    mockMvc.perform(post("/api/inscricoes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(10))
        .andExpect(jsonPath("$.status").value("CONFIRMADA"))
        .andExpect(jsonPath("$.tituloEvento").value("Workshop Java"))
        .andExpect(jsonPath("$.nomeUsuario").value("Carla"));

    }

    @Test
    void deveBuscarInscricaoPorId() throws Exception {
      
    Evento evento = Evento.builder()
        .id(1L)
        .titulo("Workshop Java")
        .build();

    Usuario usuario = Usuario.builder()
        .id(2L)
        .nome("Carla")
        .build();

    Inscricao inscricao = Inscricao.builder()
        .id(10L)
        .evento(evento)
        .usuario(usuario)
        .data(LocalDateTime.now())
        .status(StatusInscricao.CONFIRMADA)
        .build();

    when(inscricaoService.buscar(10L)).thenReturn(inscricao);

    mockMvc.perform(get("/api/inscricoes/10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(10))
        .andExpect(jsonPath("$.status").value("CONFIRMADA"))
        .andExpect(jsonPath("$.tituloEvento").value("Workshop Java"))
        .andExpect(jsonPath("$.nomeUsuario").value("Carla"));

    }

    @Test
    void deveAtualizarStatusDaInscricao() throws Exception {
       
InscricaoRequestDTO request = new InscricaoRequestDTO(
        null, 1L, 2L, StatusInscricao.CANCELADO
    );

    Evento evento = Evento.builder()
        .id(1L)
        .titulo("Workshop Java")
        .build();

    Usuario usuario = Usuario.builder()
        .id(2L)
        .nome("Carla")
        .build();

    Inscricao inscricao = Inscricao.builder()
        .id(10L)
        .evento(evento)
        .usuario(usuario)
        .data(LocalDateTime.now())
        .status(StatusInscricao.CANCELADO)
        .build();

    when(inscricaoService.atualizarStatus(eq(10L), eq(StatusInscricao.CANCELADO)))
        .thenReturn(inscricao);

    mockMvc.perform(put("/api/inscricoes/10")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(10))
        .andExpect(jsonPath("$.status").value("CANCELADO"))
        .andExpect(jsonPath("$.tituloEvento").value("Workshop Java"))
        .andExpect(jsonPath("$.nomeUsuario").value("Carla"));

    }

    @Test
    void deveExcluirInscricao() throws Exception {
        doNothing().when(inscricaoService).excluir(10L);

        mockMvc.perform(delete("/api/inscricoes/10"))
            .andExpect(status().isNoContent());
    }
}
