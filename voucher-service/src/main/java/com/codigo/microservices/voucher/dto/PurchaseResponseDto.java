package com.codigo.microservices.voucher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseResponseDto {
    private String message;
    private Boolean success;
    private List<PromoCodeDto> promoCodes;
}
