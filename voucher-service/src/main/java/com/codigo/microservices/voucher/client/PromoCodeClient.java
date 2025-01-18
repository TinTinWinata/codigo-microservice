package com.codigo.microservices.voucher.client;

import com.codigo.microservices.voucher.dto.GetUnownedPromoCodeRequestDto;
import com.codigo.microservices.voucher.dto.PromoCodeDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PromoCodeClient {
    private final WebClient webClient;

    public PromoCodeClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://127.0.0.1:8081/").build();
    }

    public Mono<List<PromoCodeDto>> getUnownedPromoCodes(GetUnownedPromoCodeRequestDto getUnownedPromoCodeRequestDto) {
        return webClient.post()
                .uri("/promo/take")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(getUnownedPromoCodeRequestDto)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<PromoCodeDto>() {})
                .collectList();
    }
}
