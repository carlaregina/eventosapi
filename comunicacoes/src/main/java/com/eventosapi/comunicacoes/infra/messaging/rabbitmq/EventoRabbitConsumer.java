package com.eventosapi.comunicacoes.infra.messaging.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.eventosapi.comunicacoes.application.services.EmailService;
import com.eventosapi.comunicacoes.interfaces.dto.InscricaoDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventoRabbitConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "${broker.queue.evento.atualizar}")
    public void receberMensagemEvento(InscricaoDTO mensagem) {
        log.info("Mensagem recebida na fila de eventos: {}", mensagem);
        emailService.enviarComAnexo(mensagem);
    }
}
