package com.eventosapi.comunicacoes.application.services;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.eventosapi.comunicacoes.application.port.UsuarioClientPort;
import com.eventosapi.comunicacoes.domain.enums.StatusInscricao;
import com.eventosapi.comunicacoes.domain.model.Usuario;
import com.eventosapi.comunicacoes.interfaces.dto.InscricaoDTO;
import com.eventosapi.comunicacoes.interfaces.dto.InscricaoVoucherDTO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final PDFService pdfService;
    private final UsuarioClientPort usuarioClient;

    public EmailService(JavaMailSender mailSender, PDFService pdfService, UsuarioClientPort usuarioClient) {
        this.mailSender = mailSender;
        this.pdfService = pdfService;
        this.usuarioClient = usuarioClient;
    }

    public void enviarComAnexo(InscricaoDTO inscricao) {
        byte[] pdf = pdfService.geraRelatorioPDF(inscricao);
        Usuario usuario = usuarioClient.findById(inscricao.getIdUsuario());
        String email = usuario.getEmail();

        String assunto = "Confira os detalhes do seu evento";
        String corpo = "Segue em anexo seu voucher em PDF. Aproveite o evento!";

        try {
            MimeMessage mensagem = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true);

            helper.setTo(email);
            helper.setSubject(assunto);
            helper.setText(corpo);
            helper.addAttachment("voucher.pdf", new ByteArrayResource(pdf));

            mailSender.send(mensagem);

            Thread.sleep(1000);

        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail", e);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public void enviarVoucherInscricao(InscricaoVoucherDTO inscricaoVoucher) {
        // Converter InscricaoVoucherDTO para InscricaoDTO para reutilizar o PDF service
        InscricaoDTO inscricaoDTO = new InscricaoDTO();
        inscricaoDTO.setId(inscricaoVoucher.getId());
        inscricaoDTO.setIdEvento(inscricaoVoucher.getIdEvento());
        inscricaoDTO.setIdUsuario(inscricaoVoucher.getIdUsuario());
        inscricaoDTO.setData(inscricaoVoucher.getData());
      inscricaoDTO.setStatus(StatusInscricao.valueOf(inscricaoVoucher.getStatus()));

        byte[] pdf = pdfService.geraRelatorioPDF(inscricaoDTO);
        Usuario usuario = usuarioClient.findById(inscricaoVoucher.getIdUsuario());
        String email = usuario.getEmail();

        String assunto = "Bem-vindo! Sua inscrição foi confirmada";
        String corpo = "Parabéns! Sua inscrição foi realizada com sucesso. Segue em anexo seu voucher de inscrição em PDF.";

        try {
            MimeMessage mensagem = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true);

            helper.setTo(email);
            helper.setSubject(assunto);
            helper.setText(corpo);
            helper.addAttachment("voucher-inscricao.pdf", new ByteArrayResource(pdf));

            mailSender.send(mensagem);

            Thread.sleep(1000);

        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail de inscrição", e);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}

