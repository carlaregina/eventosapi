package com.eventosapi.comunicacoes.application.services;

import java.util.Map;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.eventosapi.comunicacoes.domain.model.Email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviar(Email email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email.getTo());
            helper.setSubject(email.getSubject());
            helper.setText(email.getBody());

            if (email.getAttachments() != null) {
                for (Map.Entry<String, byte[]> entry : email.getAttachments().entrySet()) {
                    helper.addAttachment(entry.getKey(), new ByteArrayResource(entry.getValue()));
                }
            }

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Erro ao enviar e-mail", e);
            throw new RuntimeException("Erro ao enviar e-mail", e);
        }
    }

}
