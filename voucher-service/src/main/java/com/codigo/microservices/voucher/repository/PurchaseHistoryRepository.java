package com.codigo.microservices.voucher.repository;

import com.codigo.microservices.voucher.entity.PurchaseHistory;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface PurchaseHistoryRepository extends R2dbcRepository<PurchaseHistory, Long> {
    Flux<PurchaseHistory> findByUserPhoneAndVoucherId(String userPhone, Long voucherId);
    Flux<PurchaseHistory> findByToPhoneAndVoucherId(String toPhone, Long voucherId);
}
