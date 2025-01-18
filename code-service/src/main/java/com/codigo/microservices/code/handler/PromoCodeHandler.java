package com.codigo.microservices.code.handler;

import com.codigo.microservices.code.dto.GetPromoCodeStatusDto;
import com.codigo.microservices.code.dto.GetPurchaseHistoryDto;
import com.codigo.microservices.code.dto.GetUnownedPromoCodeRequestDto;
import com.codigo.microservices.code.dto.UpdatePromoCodeStatusDto;
import com.codigo.microservices.code.entity.PromoCode;
import com.codigo.microservices.code.service.PromoCodeService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class PromoCodeHandler {
    private final PromoCodeService promoCodeService;

    public PromoCodeHandler(PromoCodeService promoCodeService) {
        this.promoCodeService = promoCodeService;
    }

    public Mono<ServerResponse> updatePromoCodeStatus(ServerRequest request) {
        return request.bodyToMono(UpdatePromoCodeStatusDto.class)
                .flatMap(promoCodeService::updateStatusPromoCode)
                .flatMap(promoCode -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(promoCode), PromoCode.class));
    }

    public Mono<ServerResponse> getPurchaseHistoryByPhone(ServerRequest request){
        String phone = request.pathVariable("phone");
        return promoCodeService.getPurchaseHistoryByPhone(phone)
                .flatMap(purchaseHistory -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(purchaseHistory), GetPurchaseHistoryDto.class));
    }

    public Mono<ServerResponse> getPromoCodeStatusByCode(ServerRequest request){
        String code = request.pathVariable("code");
        return promoCodeService.getPromoCodeStatusByCode(code)
                        .flatMap(promoCodeStatus -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(promoCodeStatus), GetPromoCodeStatusDto.class))
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Promom code not found")));
    }

    public Mono<ServerResponse> getUnownedPromoCodes(ServerRequest request) {
        return request.bodyToMono(GetUnownedPromoCodeRequestDto.class)
                .flatMap((GetUnownedPromoCodeRequestDto getUnownedPromoCodeRequestDto) -> {
                    Flux<PromoCode> promoCode = promoCodeService.getUnownedPromoCodes(getUnownedPromoCodeRequestDto);
                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(promoCode, PromoCode.class);
                });
    }
}