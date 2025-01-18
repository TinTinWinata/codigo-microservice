package com.codigo.microservices.code.route;

import com.codigo.microservices.code.constant.PropertyConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.nio.file.Paths;


@Configuration
public class QRCodeRoute {
    @Bean
    public RouterFunction<ServerResponse> qrCodeRoutes() {
        return RouterFunctions.route(RequestPredicates.GET("/qr/{filename}"), request -> {
            String filename = request.pathVariable("filename");
            PathResource resource = new PathResource(Paths.get(PropertyConstant.QR_CODES_DIR, filename));

            if (!resource.exists()) {
                return ServerResponse.status(HttpStatus.NOT_FOUND).build();
            }

            return ServerResponse.ok()
                    .bodyValue(resource);
        }).andRoute(RequestPredicates.GET("/hello"), request -> ServerResponse.ok().build());
    }

}
