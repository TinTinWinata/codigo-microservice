package com.codigo.microservices.voucher.handler;

import com.codigo.microservices.voucher.dto.*;
import com.codigo.microservices.voucher.utility.RequestUtility;
import com.codigo.microservices.voucher.entity.Voucher;
import com.codigo.microservices.voucher.service.VoucherService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

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

    public Mono<ServerResponse> getCheckoutPrice(ServerRequest request) {
        return request.bodyToMono(RequestCheckoutPriceDto.class)
                .flatMap(voucherService::getCheckoutPrice)
                .flatMap(checkoutPrice -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(checkoutPrice), ResponseCheckoutPriceDto.class));
    }

    public Mono<ServerResponse> createVoucherDiscount(ServerRequest request) {
        return request.bodyToMono(VoucherDiscountDto.class)
                .flatMap(voucherService::createVoucherDiscount)
                .flatMap(voucher -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(voucher), Voucher.class));
    }

    public Mono<ServerResponse> updateVoucher(ServerRequest request){
        return RequestUtility.extractLong(request, "id")
                .flatMap(id -> request.bodyToMono(VoucherDto.class)
                .flatMap(voucher -> voucherService.updateVoucher(id, voucher))
                .flatMap(updatedVoucher -> {
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(Mono.just(updatedVoucher), VoucherDto.class);
                }));
    }

    public Mono<ServerResponse> updateVoucherStatus(ServerRequest request){
        return RequestUtility.extractLong(request, "id")
                .flatMap(id -> request.bodyToMono(UpdateVoucherStatusDto.class)
                        .flatMap(updateVoucherStatusDto -> voucherService.updateVoucherStatus(id, updateVoucherStatusDto))
                        .flatMap(updatedVoucher -> {
                            return ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(Mono.just(updatedVoucher), Voucher.class);
                        }));
    }

    public Mono<ServerResponse> deleteVoucher(ServerRequest request){
        return RequestUtility.extractLong(request, "id")
                .flatMap(id -> voucherService.deleteVoucher(id)
                .then(ServerResponse.noContent().build())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Voucher not found"))));
    }

    public Mono<ServerResponse> getAllVouchers(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(voucherService.getAllVouchers(), Voucher.class);
    }

    public Mono<ServerResponse> getVoucherById(ServerRequest request){
        return RequestUtility.extractLong(request, "id")
                        .flatMap(id -> voucherService.getVoucherByIdWithDiscount(id)
                        .flatMap(voucher -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(voucher), Voucher.class))
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Voucher not found"))));
    }
}
