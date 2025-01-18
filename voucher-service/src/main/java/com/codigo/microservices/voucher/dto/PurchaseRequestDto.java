package com.codigo.microservices.voucher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequestDto {
    private String voucherId;
    private String paymentMethodId;
    private int quantity;
    private String paymentMetaId;
    private String phoneNumber;
}
