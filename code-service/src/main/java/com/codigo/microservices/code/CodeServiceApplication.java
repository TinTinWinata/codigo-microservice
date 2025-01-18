package com.codigo.microservices.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class CodeServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CodeServiceApplication.class, args);
	}
}
