package com.codigo.microservices.voucher.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "voucher_discounts")
public class VoucherDiscount {
    @Id
    private Long id;

    @Column("voucher_id")
    private Long voucherId;

    @Column("payment_method_id")
    private Long paymentMethodId;

    @Column("discount")
    private BigDecimal discount;

    @Column("discount_description")
    private String discountDescription;
}
