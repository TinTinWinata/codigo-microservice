package com.codigo.microservices.api.gateway.route;

import com.codigo.microservices.api.gateway.handler.AuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class AuthRoute {
    @Bean
    public RouterFunction<ServerResponse> authRoutes(AuthHandler handler){
        return RouterFunctions.route(RequestPredicates.POST("api/auth"), handler::loginUser)
                .andRoute(RequestPredicates.GET("api/auth/refresh"), handler::refreshToken);
    }
}
