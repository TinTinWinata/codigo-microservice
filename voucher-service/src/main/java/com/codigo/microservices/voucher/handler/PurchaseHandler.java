package com.codigo.microservices.voucher.handler;

import com.codigo.microservices.voucher.dto.PurchaseRequestDto;
import com.codigo.microservices.voucher.dto.PurchaseResponseDto;
import com.codigo.microservices.voucher.dto.VoucherDto;
import com.codigo.microservices.voucher.entity.Voucher;
import com.codigo.microservices.voucher.service.PurchaseService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class PurchaseHandler
{
    private final PurchaseService purchaseService;
    public PurchaseHandler(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    public Mono<ServerResponse> purchaseVoucher(ServerRequest request) {
        return request.bodyToMono(PurchaseRequestDto.class)
                .flatMap(purchaseService::purchaseVoucher)
                .flatMap(purchaseResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(purchaseResponse), PurchaseResponseDto.class));
    }
}
