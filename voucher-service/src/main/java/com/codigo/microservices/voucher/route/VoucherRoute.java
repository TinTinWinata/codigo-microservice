package com.codigo.microservices.voucher.route;

import com.codigo.microservices.voucher.handler.VoucherHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class VoucherRoute {
    @Bean
    public RouterFunction<ServerResponse> voucherRoutes(VoucherHandler handler){
        return RouterFunctions.route(RequestPredicates.GET("/voucher/"), handler::getAllVouchers)
                .andRoute(RequestPredicates.POST("/voucher/"), handler::createVoucher)
                .andRoute(RequestPredicates.POST("/voucher/checkout"), handler::getCheckoutPrice)
                .andRoute(RequestPredicates.DELETE("/voucher/{id}"), handler::deleteVoucher)
                .andRoute(RequestPredicates.PUT("/voucher/{id}"), handler::updateVoucher)
                .andRoute(RequestPredicates.PUT("/voucher/status/{id}"), handler::updateVoucherStatus)
                .andRoute(RequestPredicates.GET("/voucher/{id}"), handler::getVoucherById)
                .andRoute(RequestPredicates.POST("/voucher/discount"), handler::createVoucherDiscount)
                .andRoute(RequestPredicates.POST("/voucher/discount"), handler::createVoucherDiscount);
    }
}
