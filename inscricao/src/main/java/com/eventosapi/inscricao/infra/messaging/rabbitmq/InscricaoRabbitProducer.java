package com.eventosapi.inscricao.infra.messaging.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.eventosapi.inscricao.interfaces.dto.InscricaoVoucherDTO;
import com.eventosapi.inscricao.application.port.InscricaoPublisherPort;

@Component
public class InscricaoRabbitProducer implements InscricaoPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.inscricao.criada}")
    private String routingKey;

    public InscricaoRabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publicarInscricaoCriada(InscricaoVoucherDTO inscricao) {
        rabbitTemplate.convertAndSend(routingKey, inscricao);
    }

}
