package com.eventosapi.demo.services;

import com.eventosapi.demo.dtos.InscricaoRequestDTO;
import com.eventosapi.demo.models.Evento;
import com.eventosapi.demo.models.Local;
import com.eventosapi.demo.models.Usuario;
import com.eventosapi.demo.repositories.EventoRepository;
import com.eventosapi.demo.repositories.UsuarioRepository;
import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VoucherServiceTest {

    private EventoRepository eventoRepository;
    private UsuarioRepository usuarioRepository;
    private VoucherService voucherService;

    @BeforeEach
    void setUp() {
        eventoRepository = mock(EventoRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        voucherService = new VoucherService(eventoRepository, usuarioRepository);
    }

    @Test
    void geraRelatorioPDF_deveRetornarByteArray_quandoInscricaoValida() throws JRException {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Tatiana");

        Local local = new Local();
        local.setNome("Auditório Principal");

        Evento evento = new Evento();
        evento.setId(1L);
        evento.setTitulo("Java Meetup");
        evento.setData(LocalDateTime.of(2025, 10, 1, 19, 0));
        evento.setLocal(local);

        InscricaoRequestDTO inscricao = new InscricaoRequestDTO(123L, 1L, 1L, null);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));

        byte[] pdf = voucherService.geraRelatorioPDF(inscricao);

        assertNotNull(pdf);
        assertTrue(pdf.length > 0);

        verify(usuarioRepository).findById(1L);
        verify(eventoRepository, times(3)).findById(1L); // é chamado 3x no voucherService
    }

    @Test
    void geraRelatorioPDF_deveLancarException_quandoUsuarioNaoEncontrado() {
        InscricaoRequestDTO inscricao = new InscricaoRequestDTO(123L, 1L, 1L, null);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                voucherService.geraRelatorioPDF(inscricao)
        );

        assertTrue(exception.getMessage().contains("No value present"));
    }

    @Test
    void geraRelatorioPDF_deveLancarException_quandoEventoNaoEncontrado() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Tatiana");

        InscricaoRequestDTO inscricao = new InscricaoRequestDTO(123L, 1L, 1L, null);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(eventoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                voucherService.geraRelatorioPDF(inscricao)
        );

        assertTrue(exception.getMessage().contains("No value present"));
    }
}
