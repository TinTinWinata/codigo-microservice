package com.codigo.microservices.voucher.entity;

import com.codigo.microservices.voucher.enums.VoucherBuyType;
import com.codigo.microservices.voucher.enums.VoucherStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vouchers")
public class Voucher {
    @Id
    private Long id;

    @Column("title")
    private String title;

    @Column("description")
    private String description;

    @Column("expiry_date")
    private LocalDate expiryDate;

    @Column("image_url")
    private String imageUrl;

    @Column("amount")
    private BigDecimal amount;

    @Column("quantity")
    private Integer quantity;

    @Column("buy_type")
    private VoucherBuyType buyType;

    @Column("status")
    private VoucherStatus status;

    @Column("max_buy_limit")
    private int maxBuyLimit;

    @Column("max_user_limit_from_gift")
    private int maxUserLimitFromGift;
}
