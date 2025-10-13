package com.eventosapi.comunicacoes.infra.messaging.rabbitmq;

import com.eventosapi.comunicacoes.interfaces.dto.InscricaoVoucherDTO;
import com.eventosapi.comunicacoes.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class InscricaoRabbitConsumer {

    private final EmailService emailService;

    public InscricaoRabbitConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${broker.queue.inscricao.criada}")
    public void receberInscricaoCriada(InscricaoVoucherDTO mensagem) {
        emailService.enviarVoucherInscricao(mensagem);
    }
}