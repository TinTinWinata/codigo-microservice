package com.codigo.microservices.code.route;

import com.codigo.microservices.code.handler.PromoCodeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
@Configuration
public class PromoCodeRoute {
    @Bean
    public RouterFunction<ServerResponse> purchaseRoutes(PromoCodeHandler handler){
        return RouterFunctions.route(RequestPredicates.POST("/promo/take"), handler::getUnownedPromoCodes);
    }
}

