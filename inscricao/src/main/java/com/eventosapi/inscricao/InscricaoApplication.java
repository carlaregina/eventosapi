package com.eventosapi.inscricao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.eventosapi")
@SpringBootApplication
public class InscricaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(InscricaoApplication.class, args);
	}

}
