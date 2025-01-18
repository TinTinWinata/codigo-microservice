package com.codigo.microservices.code.entity;

import com.codigo.microservices.code.enums.VoucherBuyType;
import com.codigo.microservices.code.enums.VoucherStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "promo_codes")
public class PromoCode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "qr_code", nullable = false)
    private String qrCode;

    @Column(name = "voucher_id", nullable = false)
    private String voucherId;

    @Enumerated(EnumType.STRING)
    @Column(name = "buy_type")
    private VoucherBuyType buyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private VoucherStatus status;

    @Column(name = "owner_phone")
    private String ownerPhone;

    @Column(name = "generated_date", nullable = false)
    private LocalDateTime generatedDate;

    @Column(name = "bought_date")
    private LocalDateTime boughtDate;
}
