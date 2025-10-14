package com.eventosapi.comunicacoes.infra.messaging.rabbitmq;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.eventosapi.comunicacoes.application.services.EmailService;
import com.eventosapi.comunicacoes.application.services.PDFService;
import com.eventosapi.comunicacoes.domain.model.Email;
import com.eventosapi.comunicacoes.domain.model.Inscricao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class InscricaoRabbitConsumer {

    private final PDFService pdfService;
    private final EmailService emailService;

    @RabbitListener(queues = "${broker.queue.inscricao.criada}")
    public void receberInscricaoCriada(Inscricao inscricao) {
        log.info("Mensagem recebida na fila de inscrição criada: {}", inscricao);
        byte[] pdf = pdfService.geraRelatorioPDF(inscricao);
        Email email = Email.builder()
            .to(inscricao.getUsuario().getEmail())
            .subject("Bem-vindo! Sua inscrição foi confirmada")
            .body("Parabéns! Sua inscrição foi realizada com sucesso. Segue em anexo seu voucher de inscrição em PDF.")
            .attachments(Map.of("voucher.pdf", pdf))
            .build();
        emailService.enviar(email);
    }
}