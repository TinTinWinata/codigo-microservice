package com.codigo.microservices.voucher;

import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public class RequestUtility {
    public static Mono<Long> extractLong(ServerRequest request, String variableName) {
        try {
            return Mono.just(Long.parseLong(request.pathVariable(variableName)));
        } catch (NumberFormatException e) {
            return Mono.error(new IllegalArgumentException("Invalid ID format. Must be a number."));
        }
    }
}
