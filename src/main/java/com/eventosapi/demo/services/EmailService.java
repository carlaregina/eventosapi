package com.eventosapi.demo.services;
import com.eventosapi.demo.dtos.InscricaoRequestDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final VoucherService voucherService;

    public EmailService(JavaMailSender mailSender, VoucherService voucherService) {
        this.mailSender = mailSender;
        this.voucherService = voucherService;
    }

    public void enviarComAnexo(String para, String assunto, String corpo, InscricaoRequestDTO req) {
        byte[] pdf = voucherService.geraRelatorioPDF(req);
        try {
            MimeMessage mensagem = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true);

            helper.setTo(para);
            helper.setSubject(assunto);
            helper.setText(corpo);
            helper.addAttachment("voucher.pdf", new ByteArrayResource(pdf));

            mailSender.send(mensagem);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail", e);
        }
    }
}

