package com.eventosapi.comunicacoes.infra.messaging.rabbitmq;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.eventosapi.comunicacoes.application.port.InscricaoClientPort;
import com.eventosapi.comunicacoes.application.services.EmailService;
import com.eventosapi.comunicacoes.application.services.PDFService;
import com.eventosapi.comunicacoes.domain.model.Email;
import com.eventosapi.comunicacoes.domain.model.Inscricao;
import com.eventosapi.comunicacoes.infra.dtos.InscricaoDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventoRabbitConsumer {

    private final PDFService pdfService;
    private final EmailService emailService;
    private final InscricaoClientPort inscricaoClientPort;

    @RabbitListener(queues = "${broker.queue.evento.atualizar}")
    public void receberMensagemEvento(InscricaoDTO dto) {
        log.info("Mensagem recebida na fila de inscrição criada: {}", dto.getId());
        Inscricao inscricao = inscricaoClientPort.findById(dto.getId()).get();
        byte[] pdf = pdfService.geraRelatorioPDF(inscricao);
        Email email = Email.builder()
            .to(inscricao.getUsuario().getEmail())
            .subject("Confira os detalhes do seu evento")
            .body("Segue em anexo seu voucher em PDF. Aproveite o evento!")
            .attachments(Map.of("voucher.pdf", pdf))
            .build();
        emailService.enviar(email);
    }
}
