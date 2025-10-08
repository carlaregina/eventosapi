package com.eventosapi.inscricao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.eventosapi.inscricao") 
public class InscricaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(InscricaoApplication.class, args);
	}

}
