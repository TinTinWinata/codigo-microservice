package com.codigo.microservices.voucher;

import org.springframework.boot.SpringApplication;

public class TestVoucherServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(VoucherServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
