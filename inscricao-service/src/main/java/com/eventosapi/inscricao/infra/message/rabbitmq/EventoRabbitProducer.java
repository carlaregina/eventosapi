package com.eventosapi.inscricao.infra.message.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import com.eventosapi.inscricao.application.port.EventoPublisherPort;
import com.eventosapi.inscricao.interfaces.dto.InscricaoResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class EventoRabbitProducer implements EventoPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    @Value("${inscricao.create.comunicacoes}")
    private String routingKey;


    public EventoRabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publicarEvento(EventoResponseDTO evento) {
        rabbitTemplate.convertAndSend(routingKey, evento);
    }
    
}
