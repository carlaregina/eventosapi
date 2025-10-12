package com.eventosapi.comunicacoes.infra.messaging.rabbitmq;

import com.eventosapi.comunicacoes.interfaces.dto.InscricaoDTO;
import com.eventosapi.comunicacoes.services.EmailService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventoRabbitConsumer {

    private final EmailService emailService;

    public EventoRabbitConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${broker.queue.evento.atualizar}")
    public void receberMensagemEvento(InscricaoDTO mensagem) {
        log.info("Mensagem recebida na fila de eventos: {}", mensagem);
        emailService.enviarComAnexo(mensagem);
    }
}
