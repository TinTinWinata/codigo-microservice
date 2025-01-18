package com.codigo.microservices.voucher.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDiscountDto {
    private String voucherId;
    private BigDecimal discount;
    private String paymentMethodId;
    private String discountDescription;
}
