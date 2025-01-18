package com.codigo.microservices.voucher.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDiscountDto {
    @NotNull(message = "Voucher ID must not be null.")
    @Positive(message = "Voucher ID must be a positive number.")
    private Long voucherId;

    @NotNull(message = "Discount must not be null.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Discount must be greater than 0.")
    private BigDecimal discount;

    @NotNull(message = "Payment Method ID must not be null.")
    @Positive(message = "Payment Method ID must be a positive number.")
    private Long paymentMethodId;

    @NotBlank(message = "Discount description must not be blank.")
    private String discountDescription;
}
