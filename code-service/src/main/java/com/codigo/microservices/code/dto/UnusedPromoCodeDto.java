package com.codigo.microservices.code.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UnusedPromoCodeDto {
    private String qrCodeImages;
    private String promoCode;
}