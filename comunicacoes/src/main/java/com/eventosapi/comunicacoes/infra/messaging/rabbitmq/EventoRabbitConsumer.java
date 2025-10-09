package com.eventosapi.comunicacoes.infra.messaging.rabbitmq;


import com.eventosapi.comunicacoes.interfaces.dto.InscricaoDTO;
import com.eventosapi.comunicacoes.services.EmailService;
import com.eventosapi.comunicacoes.services.PDFService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EventoRabbitConsumer {

    private final EmailService emailService;

    public EventoRabbitConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${broker.queue.evento.atualizar}")
    public void receberMensagemEvento(InscricaoDTO mensagem) {
        System.out.println("Recebendo mensagem: " + mensagem);
        emailService.enviarComAnexo(mensagem);
    }
}
