package com.codigo.microservices.api.gateway.handler;

import com.codigo.microservices.api.gateway.dto.AuthResponseDto;
import com.codigo.microservices.api.gateway.dto.UserDto;
import com.codigo.microservices.api.gateway.entity.User;
import com.codigo.microservices.api.gateway.services.AuthService;
import com.codigo.microservices.api.gateway.utility.RequestUtility;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class AuthHandler {
    private final AuthService authService;

    public AuthHandler(AuthService authService) {
        this.authService = authService;
    }

    public Mono<ServerResponse> loginUser(ServerRequest request) {
        return request.bodyToMono(UserDto.class)
                .flatMap(authService::loginUser)
                .flatMap(authResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(authResponse), AuthResponseDto.class));
    }

    public Mono<ServerResponse> refreshToken(ServerRequest request) {
        var token = request.queryParam("refreshToken");

        if (token.isEmpty()) {
            return ServerResponse.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(Map.of("message", "Invalid token", "success", false)), Map.class);
        }

        return authService.refreshToken(token.get())
                .flatMap(newToken ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(Map.of(
                                        "token", newToken
                                )), Map.class)
                )
                .onErrorResume(e -> ServerResponse.status(401)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(Map.of(
                                "message", "Invalid or expired refresh token",
                                "success", false
                        )), Map.class)
                );
    }
}