package com.codigo.microservices.voucher.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "purchase_histories")
public class PurchaseHistory {
    @Id
    private Long id;

    @Column("user_phone")
    private String userPhone;

    @Column("user_name")
    private String userName;

    @Column("to_phone")
    private String toPhone;

    @Column("to_name")
    private String toName;

    @Column("voucher_count")
    private int voucherCount;

    @Column("voucher_id")
    private Long voucherId;

    @Column("purchase_date")
    private LocalDateTime purchaseDate;
}
