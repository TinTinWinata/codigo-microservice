package com.codigo.microservices.code;

import org.springframework.boot.SpringApplication;

public class TestCodeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(CodeServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
