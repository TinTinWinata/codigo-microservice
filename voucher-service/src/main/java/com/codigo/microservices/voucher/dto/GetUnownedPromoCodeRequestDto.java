package com.codigo.microservices.voucher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUnownedPromoCodeRequestDto {
    private String voucherId;
    private int voucherCount;
    private String phoneNumber;
}