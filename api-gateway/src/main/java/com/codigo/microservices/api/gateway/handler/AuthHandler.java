package com.codigo.microservices.api.gateway.handler;

import com.codigo.microservices.api.gateway.dto.UserDto;
import com.codigo.microservices.api.gateway.entity.User;
import com.codigo.microservices.api.gateway.services.AuthService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AuthHandler {
    private final AuthService authService;

    public AuthHandler(AuthService authService) {
        this.authService = authService;
    }

    public Mono<ServerResponse> loginUser(ServerRequest request) {
        return request.bodyToMono(UserDto.class)
                .flatMap(authService::loginUser)
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(user), User.class));
    }
}