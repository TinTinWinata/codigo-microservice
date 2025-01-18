package com.codigo.microservices.voucher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCheckoutPriceDto {
    private BigDecimal totalPrice;
    private BigDecimal discount;
    private BigDecimal voucherPrice;
}
