package com.codigo.microservices.code.handler;

import com.codigo.microservices.code.dto.GetUnownedPromoCodeRequestDto;
import com.codigo.microservices.code.entity.PromoCode;
import com.codigo.microservices.code.service.PromoCodeService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PromoCodeHandler {
    private final PromoCodeService promoCodeService;

    public PromoCodeHandler(PromoCodeService promoCodeService) {
        this.promoCodeService = promoCodeService;
    }

    public Mono<ServerResponse> getUnownedPromoCodes(ServerRequest request) {
        return request.bodyToMono(GetUnownedPromoCodeRequestDto.class)
                .flatMap((GetUnownedPromoCodeRequestDto getUnownedPromoCodeRequestDto) -> {
                    Flux<PromoCode> promoCode = promoCodeService.getUnownedPromoCodes(getUnownedPromoCodeRequestDto);
                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(promoCode, PromoCode.class);
                });
    }
}