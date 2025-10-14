package com.eventosapi.comunicacoes.infra.messaging.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.eventosapi.comunicacoes.application.services.VoucherService;
import com.eventosapi.comunicacoes.interfaces.dto.InscricaoDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventoRabbitConsumer {

    private final VoucherService service;

    @RabbitListener(queues = "${broker.queue.evento.atualizar}")
    public void receberMensagemEvento(InscricaoDTO dto) {
        log.info("Mensagem recebida na fila de eventos: {}", dto);
        service.enviarVoucherAtualizado(dto);
    }
}
