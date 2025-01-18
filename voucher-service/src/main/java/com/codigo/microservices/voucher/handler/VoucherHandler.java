package com.codigo.microservices.voucher.handler;

import com.codigo.microservices.voucher.dto.VoucherDto;
import com.codigo.microservices.voucher.entity.Voucher;
import com.codigo.microservices.voucher.service.VoucherService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class VoucherHandler {
    private final VoucherService voucherService;

    public VoucherHandler(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Mono<ServerResponse> createVoucher(ServerRequest request) {
        return request.bodyToMono(VoucherDto.class)
                .flatMap(voucherService::createVoucher)
                .flatMap(voucher -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(voucher), Voucher.class));
    }

    public Mono<ServerResponse> updateVoucher(ServerRequest request){
        UUID id = UUID.fromString(request.pathVariable("id"));
        return request.bodyToMono(VoucherDto.class)
                .flatMap(voucher -> voucherService.updateVoucher(id, voucher))
                .flatMap(updatedVoucher -> {
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(Mono.just(updatedVoucher), Voucher.class);
                });
    }

    public Mono<ServerResponse> deleteVoucher(ServerRequest request){
        UUID id = UUID.fromString(request.pathVariable("id"));
        return voucherService.deleteVoucher(id)
                .then(ServerResponse.noContent().build())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Voucher not found")));
    }

    public Mono<ServerResponse> getAllVouchers(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(voucherService.getAllVouchers(), Voucher.class);
    }

    public Mono<ServerResponse> getVoucherById(ServerRequest request){
        UUID id = UUID.fromString(request.pathVariable("id"));
        return voucherService.getVoucherById(id)
                .flatMap(voucher -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(voucher), Voucher.class))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Voucher not found")));
    }
}
