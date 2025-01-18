package com.codigo.microservices.voucher.route;

import com.codigo.microservices.voucher.handler.PurchaseHandler;
import com.codigo.microservices.voucher.handler.VoucherHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class PurchaseRoute {
    @Bean
    public RouterFunction<ServerResponse> purchaseRoutes(PurchaseHandler handler){
        return RouterFunctions.route(RequestPredicates.POST("/purchase/voucher"), handler::purchaseVoucher);
    }
}
