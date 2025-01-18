package com.codigo.microservices.voucher.handler;

import com.codigo.microservices.voucher.dto.VoucherDto;
import com.codigo.microservices.voucher.entity.Voucher;
import com.codigo.microservices.voucher.service.PaymentMethodService;
import com.codigo.microservices.voucher.service.VoucherService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class PaymentMethodHandler {
    private final PaymentMethodService paymentMethodService;

    public PaymentMethodHandler(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    public Mono<ServerResponse> getAllPaymentMethods(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(paymentMethodService.getAllPaymentMethods(), Voucher.class);
    }
}
