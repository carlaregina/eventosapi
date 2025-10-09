package com.eventosapi.inscricao.interfaces.controller;

import com.eventosapi.inscricao.application.services.InscricaoService;
import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.exception.GlobalExceptionHandler;
import com.eventosapi.inscricao.exception.EntidadeNaoEncontradoException;

import com.eventosapi.inscricao.interfaces.dto.FiltroInscricaoDTO;
import com.eventosapi.inscricao.interfaces.dto.InscricaoRequestDTO;
import com.eventosapi.inscricao.interfaces.dto.InscricaoResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = InscricaoController.class)
@AutoConfigureMockMvc
@Import(GlobalExceptionHandler.class) 
class InscricaoControllerTest {

  @Autowired MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;


  @MockBean InscricaoService service;

  @Test
  void deveCriarInscricaoComSucesso() throws Exception {
    var req  = new InscricaoRequestDTO(1L, 2L, StatusInscricao.CONFIRMADA);
    var resp = new InscricaoResponseDTO(
        10L,        
        1L,         
        2L,        
        StatusInscricao.CONFIRMADA,
        LocalDateTime.now()
    );

    when(service.criar(eq(req))).thenReturn(resp);

    mockMvc.perform(post("/api/inscricoes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(10))
        .andExpect(jsonPath("$.eventoId").value(1))
        .andExpect(jsonPath("$.usuarioId").value(2))
        .andExpect(jsonPath("$.status").value("CONFIRMADA"))
        .andExpect(jsonPath("$.data").exists());
  }

  @Test
  void deveBuscarInscricaoPorId() throws Exception {
    var resp = new InscricaoResponseDTO(
        10L,       
        1L,         
        2L,        
        StatusInscricao.PENDENTE,
        LocalDateTime.now()
    );
    when(service.buscar(10L)).thenReturn(resp);

    mockMvc.perform(get("/api/inscricoes/10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(10))
        .andExpect(jsonPath("$.eventoId").value(1))
        .andExpect(jsonPath("$.usuarioId").value(2))
        .andExpect(jsonPath("$.status").value("PENDENTE"))
        .andExpect(jsonPath("$.data").exists());
  }

  @Test
  void deveListarFiltradoEPaginado() throws Exception {
    var item = new InscricaoResponseDTO(
        10L,      
        1L,         
        2L,      
        StatusInscricao.CONFIRMADA,
        LocalDateTime.now()
    );
    when(service.listar(any(FiltroInscricaoDTO.class))).thenReturn(List.of(item));

    mockMvc.perform(get("/api/inscricoes?page=0&size=10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(10))
        .andExpect(jsonPath("$[0].eventoId").value(1))
        .andExpect(jsonPath("$[0].usuarioId").value(2))
        .andExpect(jsonPath("$[0].status").value("CONFIRMADA"))
        .andExpect(jsonPath("$[0].data").exists());
  }

  @Test
  void deveAtualizarStatus() throws Exception {
    record StatusUpdate(String status) {}
    var body = new StatusUpdate("CANCELADO");

    var resp = new InscricaoResponseDTO(
        10L,        
        1L,         
        2L,       
        StatusInscricao.CANCELADO,
        LocalDateTime.now()
    );
    when(service.atualizarStatus(10L, StatusInscricao.CANCELADO)).thenReturn(resp);

    mockMvc.perform(put("/api/inscricoes/10/status")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(10))
        .andExpect(jsonPath("$.eventoId").value(1))
        .andExpect(jsonPath("$.usuarioId").value(2))
        .andExpect(jsonPath("$.status").value("CANCELADO"))
        .andExpect(jsonPath("$.data").exists());
  }

  @Test
  void deveExcluir() throws Exception {
    doNothing().when(service).excluir(10L);

    mockMvc.perform(delete("/api/inscricoes/10"))
        .andExpect(status().isNoContent());
  }

  @Test
  void deveListarConfirmadasPorEvento_comSucesso() throws Exception {
    long eventoId = 1L;
    var item = new InscricaoResponseDTO(
        10L, eventoId, 2L, StatusInscricao.CONFIRMADA, LocalDateTime.now()
    );
    when(service.listarConfirmadasPorEvento(eq(eventoId), eq(0), eq(10)))
        .thenReturn(List.of(item));

    mockMvc.perform(get("/api/inscricoes/eventos/{eventoId}/confirmadas?page=0&size=10", eventoId)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(10))
        .andExpect(jsonPath("$[0].eventoId").value(1))
        .andExpect(jsonPath("$[0].usuarioId").value(2))
        .andExpect(jsonPath("$[0].status").value("CONFIRMADA"))
        .andExpect(jsonPath("$[0].data").exists());
  }

  @Test
  void deveRetornarListaVazia_quandoNaoHaConfirmadas() throws Exception {
    long eventoId = 2L;
    when(service.listarConfirmadasPorEvento(eq(eventoId), eq(0), eq(10)))
        .thenReturn(List.of());

    mockMvc.perform(get("/api/inscricoes/eventos/{eventoId}/confirmadas?page=0&size=10", eventoId)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.length()").value(0));
  }

  @Test
  void deveRetornar404_quandoEventoNaoEncontrado() throws Exception {
    long eventoId = 999L;
    when(service.listarConfirmadasPorEvento(eq(eventoId), eq(0), eq(10)))
        .thenThrow(new EntidadeNaoEncontradoException("Evento não encontrado"));

    mockMvc.perform(get("/api/inscricoes/eventos/{eventoId}/confirmadas?page=0&size=10", eventoId)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }


}
