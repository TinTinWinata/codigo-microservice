package com.codigo.microservices.voucher.entity;

import com.codigo.microservices.voucher.enums.VoucherBuyType;
import com.codigo.microservices.voucher.enums.VoucherStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vouchers")
@Entity
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "buy_type", nullable = false)
    private VoucherBuyType buyType;

    @Column(name = "owner_phone", length = 15)
    private String ownerPhone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VoucherStatus status;

    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoucherDiscount> voucherDiscounts = new ArrayList<>();
}
