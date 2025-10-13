package com.eventosapi.comunicacoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients(basePackages = "com.eventosapi")
@ComponentScan(basePackages = "com.eventosapi")
@SpringBootApplication
public class ComunicacoesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComunicacoesApplication.class, args);
	}

}
