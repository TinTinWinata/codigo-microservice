package com.codigo.microservices.voucher.handler;

import com.codigo.microservices.voucher.dto.PurchaseRequestDto;
import com.codigo.microservices.voucher.dto.PurchaseResponseDto;
import com.codigo.microservices.voucher.dto.VoucherDto;
import com.codigo.microservices.voucher.entity.Voucher;
import com.codigo.microservices.voucher.service.PurchaseService;
import com.codigo.microservices.voucher.utility.RequestUtility;
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
        return RequestUtility.getUser(request)
                .flatMap(user -> request.bodyToMono(PurchaseRequestDto.class)
                        .flatMap(purchaseRequestDto ->
                                purchaseService.purchaseVoucher(user, purchaseRequestDto)
                        )
                        .flatMap(purchaseResponse -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(purchaseResponse)
                        )
                )
                .onErrorResume(e -> ServerResponse.badRequest()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(e.getMessage())
                );
    }

}
