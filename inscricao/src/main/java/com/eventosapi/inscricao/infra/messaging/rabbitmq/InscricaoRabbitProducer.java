package com.eventosapi.inscricao.infra.messaging.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import com.eventosapi.inscricao.application.dto.InscricaoResponseDTO;
import com.eventosapi.inscricao.application.port.InscricaoPublisherPort;

@Component
public class InscricaoRabbitProducer implements InscricaoPublisherPort {

    private final RabbitTemplate rabbitTemplate;


        @Value("${inscricao.create.comunicacoes}")
    private String routingKey;

    public InscricaoRabbitProducer(RabbitTemplate rabbitTemplate, Queue queueEventoAtualizar) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publicarInscricaoCriada(InscricaoResponseDTO inscricao) {
        rabbitTemplate.convertAndSend(routingKey, inscricao);
    }
    
}
