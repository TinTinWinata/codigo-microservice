package com.codigo.microservices.api.gateway.utility;

import com.codigo.microservices.api.gateway.entity.User;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public class RequestUtility {
    public static Mono<User> getUser(ServerRequest request) {
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
