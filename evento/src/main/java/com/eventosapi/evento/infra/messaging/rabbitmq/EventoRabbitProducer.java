package com.eventosapi.evento.infra.messaging.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eventosapi.evento.application.port.EventoPublisherPort;
import com.eventosapi.evento.interfaces.dto.InscricaoDTO;

@Component
public class EventoRabbitProducer implements EventoPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.evento.atualizar}")
    private String routingKey;


    public EventoRabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publicarEvento(InscricaoDTO dto) {
        rabbitTemplate.convertAndSend(routingKey, dto);
    }
}

