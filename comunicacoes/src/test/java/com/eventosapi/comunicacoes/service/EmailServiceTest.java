package com.eventosapi.comunicacoes.service;

import com.eventosapi.comunicacoes.application.port.UsuarioClientPort;
import com.eventosapi.comunicacoes.domain.model.Usuario;
import com.eventosapi.comunicacoes.interfaces.dto.InscricaoDTO;
import com.eventosapi.comunicacoes.services.EmailService;
import com.eventosapi.comunicacoes.services.PDFService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.internet.MimeMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private PDFService pdfService;

    @Mock
    private UsuarioClientPort usuarioClient;

    @InjectMocks
    private EmailService emailService;

    @Captor
    private ArgumentCaptor<MimeMessage> mimeMessageCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveEnviarEmailComAnexo() throws Exception {
        byte[] pdfBytes = "pdf fake".getBytes();
        when(pdfService.geraRelatorioPDF(any())).thenReturn(pdfBytes);

        Usuario usuario = new Usuario();
        usuario.setEmail("teste@dominio.com");
        when(usuarioClient.findById(anyLong())).thenReturn(usuario);

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        InscricaoDTO inscricaoDTO = new InscricaoDTO();
        inscricaoDTO.setIdUsuario(1L);

        emailService.enviarComAnexo(inscricaoDTO);

        verify(mailSender, times(1)).createMimeMessage();
        verify(mailSender, times(1)).send(mimeMessage);
        verify(pdfService, times(1)).geraRelatorioPDF(inscricaoDTO);
        verify(usuarioClient, times(1)).findById(1L);
    }
}
