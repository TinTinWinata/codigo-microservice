package com.codigo.microservices.voucher.handler;


import com.codigo.microservices.voucher.dto.ErrorResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class ErrorHandler implements WebExceptionHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(org.springframework.web.server.ServerWebExchange exchange, Throwable ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(false, getErrorMessage(ex));

        exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return exchange.getResponse().writeWith(
                Mono.fromSupplier(() -> {
                    try {
                        byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);
                        return exchange.getResponse().bufferFactory().wrap(bytes);
                    } catch (Exception e) {
                        throw new RuntimeException("Error writing JSON response", e);
                    }
                })
        );
    }
    private String getErrorMessage(Throwable ex) {
        if (ex instanceof ResponseStatusException) {
            return ((ResponseStatusException) ex).getReason();
        }
        return ex.getMessage() != null ? ex.getMessage() : "Unexpected error occurred";
    }
}