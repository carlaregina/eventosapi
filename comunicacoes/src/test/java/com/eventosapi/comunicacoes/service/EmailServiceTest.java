package com.eventosapi.comunicacoes.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import com.eventosapi.comunicacoes.application.services.EmailService;
import com.eventosapi.comunicacoes.domain.model.Email;

import jakarta.mail.internet.MimeMessage;

public class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

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
        
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        Email email = Email.builder()
            .to("teste@dominio.com")
            .subject("Confirmação de Inscrição")
            .body("Sua inscrição foi confirmada.")
            .attachments(Map.of("voucher.pdf", pdfBytes))
            .build();

        emailService.enviar(email);

        verify(mailSender, times(1)).createMimeMessage();
        verify(mailSender, times(1)).send(mimeMessage);
    }
}
