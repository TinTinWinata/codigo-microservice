package com.codigo.microservices.voucher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class VoucherServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(VoucherServiceApplication.class, args);
	}
}