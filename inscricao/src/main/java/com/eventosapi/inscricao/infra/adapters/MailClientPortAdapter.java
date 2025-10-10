package com.eventosapi.inscricao.infra.adapters;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eventosapi.inscricao.application.dtos.EmailDTO;
import com.eventosapi.inscricao.application.port.MailClientPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailClientPortAdapter implements MailClientPort {

    @Value("${broker.queue.enviar-email}")
    private String enviarEmail;

    private final RabbitTemplate rabbitTemplate;

	@Override
	public void send(EmailDTO email) {
        rabbitTemplate.convertAndSend(enviarEmail, email);
	}
    
}
