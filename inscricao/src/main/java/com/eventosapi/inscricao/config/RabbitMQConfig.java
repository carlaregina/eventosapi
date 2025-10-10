package com.eventosapi.inscricao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
public class RabbitMQConfig {

//    @Value("${inscricao.create.comunicacoes}")
//     private String eventoAtualizar;

//     @Bean
//     public Queue queueEventoAtualizar() {
//         return new Queue(eventoAtualizar, true);
//     }  

  @Bean
  Jackson2JsonMessageConverter jacksonMessageConverter(ObjectMapper mapper) {
    return new Jackson2JsonMessageConverter(mapper);
  }

  @Bean
  RabbitTemplate rabbitTemplate(ConnectionFactory cf, Jackson2JsonMessageConverter conv) {
    RabbitTemplate tpl = new RabbitTemplate(cf);
    tpl.setMessageConverter(conv);
    return tpl;
  }
    
}
