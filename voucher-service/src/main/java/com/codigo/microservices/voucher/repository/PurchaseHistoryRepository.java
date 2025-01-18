package com.codigo.microservices.voucher.repository;

import com.codigo.microservices.voucher.entity.PurchaseHistory;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface PurchaseHistoryRepository extends R2dbcRepository<PurchaseHistory, Long> {
}
