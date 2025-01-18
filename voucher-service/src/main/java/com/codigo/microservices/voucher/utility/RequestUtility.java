package com.codigo.microservices.voucher.utility;

import com.codigo.microservices.voucher.dto.UserDto;
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

    public static Mono<UserDto> getUser(ServerRequest request) {
        return Mono.justOrEmpty(request.headers().firstHeader("Authorization"))
                .flatMap(header -> {
                    if (header.startsWith("Bearer ")) {
                        String token = header.substring(7); // Extract token
                        try {
                            return Mono.just(JwtUtility.validateToken(token));
                        } catch (Exception e) {
                            return Mono.error(new RuntimeException("Invalid token", e));
                        }
                    }
                    return Mono.error(new RuntimeException("Missing or malformed Authorization header"));
                });
    }
}
