package com.codigo.microservices.voucher.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RequestCheckoutPriceDto {
    private Long voucherId;
    private BigDecimal quantity;
    private Long paymentMethodId;
}
