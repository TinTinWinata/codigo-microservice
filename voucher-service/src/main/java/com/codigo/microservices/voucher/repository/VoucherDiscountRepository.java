package com.codigo.microservices.voucher.repository;

import com.codigo.microservices.voucher.entity.VoucherDiscount;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface VoucherDiscountRepository extends R2dbcRepository<VoucherDiscount, Long> {
    Flux<VoucherDiscount> findByVoucherId(Long voucherId);
}
