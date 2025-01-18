package com.codigo.microservices.voucher.route;

import com.codigo.microservices.voucher.handler.PaymentMethodHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class PaymentMethodRoute {
    @Bean
    public RouterFunction<ServerResponse> paymentMethodRoutes(PaymentMethodHandler handler){
        return RouterFunctions.route(RequestPredicates.GET("/payment-method"), handler::getAllPaymentMethods);
    }
}
