package com.eventosapi.comunicacoes.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.eventosapi.comunicacoes.application.port.EventoClientPort;
import com.eventosapi.comunicacoes.application.port.UsuarioClientPort;
import com.eventosapi.comunicacoes.application.services.PDFService;
import com.eventosapi.comunicacoes.domain.model.Evento;
import com.eventosapi.comunicacoes.domain.model.Usuario;
import com.eventosapi.comunicacoes.interfaces.dto.InscricaoDTO;

public class PDFServiceTest {

    @Mock
    private UsuarioClientPort usuarioClient;

    @Mock
    private EventoClientPort eventoClient;

    @InjectMocks
    private PDFService pdfService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarParametrosCorretamente() {
        // Arrange
        InscricaoDTO inscricao = new InscricaoDTO();
        inscricao.setId(1L);
        inscricao.setIdUsuario(2L);
        inscricao.setIdEvento(3L);

        Usuario usuario = new Usuario();
        usuario.setNome("Tatiana");
        when(usuarioClient.findById(2L)).thenReturn(Optional.of(usuario));

        Evento evento = new Evento();
        evento.setTitulo("Evento Teste");
        evento.setData(LocalDateTime.of(2025, 10, 9, 15, 30));
        when(eventoClient.findById(3L)).thenReturn(Optional.of(evento));

        Map<String, Object> parametros = pdfService.criaParametros(inscricao);

        assertEquals("Olá, Tatiana, sua inscrição foi confirmada. Observe os detalhes do evento: ", parametros.get("SAUDACAO_USUARIO"));
        assertEquals("Número da inscrição: 1", parametros.get("NUMERO_INSCRICAO"));
        assertEquals("Evento: Evento Teste", parametros.get("NOME_EVENTO"));
        assertEquals("Horário: 15:30", parametros.get("HORARIO_EVENTO"));
        assertEquals("Local: Evento Teste", parametros.get("LOCAL_EVENTO"));

        verify(usuarioClient, times(1)).findById(2L);
        verify(eventoClient, times(3)).findById(3L); // chamado 3x para nome, horário e local
    }

    @Test
    void deveGerarPDF() {
        InscricaoDTO inscricao = new InscricaoDTO();
        inscricao.setId(1L);
        inscricao.setIdUsuario(2L);
        inscricao.setIdEvento(3L);

        Usuario usuario = new Usuario();
        usuario.setNome("Tatiana");
        when(usuarioClient.findById(2L)).thenReturn(Optional.of(usuario));

        Evento evento = new Evento();
        evento.setTitulo("Evento Teste");
        evento.setData(LocalDateTime.of(2025, 10, 9, 15, 30));
        when(eventoClient.findById(3L)).thenReturn(Optional.of(evento));

        assertDoesNotThrow(() -> {
            byte[] pdf = pdfService.geraRelatorioPDF(inscricao);
            assertNotNull(pdf);
        });
    }
}
