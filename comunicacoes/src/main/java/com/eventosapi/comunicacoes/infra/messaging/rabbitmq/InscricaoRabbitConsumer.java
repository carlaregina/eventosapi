package com.eventosapi.comunicacoes.infra.messaging.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.eventosapi.comunicacoes.application.services.EmailService;
import com.eventosapi.comunicacoes.interfaces.dto.InscricaoVoucherDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class InscricaoRabbitConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "${broker.queue.inscricao.criada}")
    public void receberInscricaoCriada(InscricaoVoucherDTO mensagem) {
        log.info("Mensagem recebida na fila de inscrição criada: {}", mensagem);
        emailService.enviarVoucherInscricao(mensagem);
    }
}