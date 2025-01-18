package com.codigo.microservices.voucher.repository;

import com.codigo.microservices.voucher.entity.VoucherDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VoucherDiscountRepository extends JpaRepository<VoucherDiscount, UUID> {
}
