package com.codigo.microservices.api.gateway.route;

import com.codigo.microservices.api.gateway.entity.User;
import com.codigo.microservices.api.gateway.handler.AuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Configuration
public class FallbackRoute {
    @Bean
    public RouterFunction<ServerResponse> fallbackRoutes() {
        return RouterFunctions.route(RequestPredicates.POST("api/fallback"), (ServerRequest request) ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(Map.of(
                                "message", "Service is temporarily unavailable. Please try again later.",
                                "success", false
                        )), Map.class)
        );
    }
}
