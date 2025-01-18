package com.codigo.microservices.voucher.repository;

import com.codigo.microservices.voucher.entity.PurchaseHistory;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.List;

public interface PurchaseHistoryRepository extends R2dbcRepository<PurchaseHistory, Long> {
    List<PurchaseHistory> findByUserPhoneAndVoucherId(String userPhone, Long voucherId);
    List<PurchaseHistory> findByToPhoneAndVoucherId(String toPhone, Long voucherId);
}
