package com.codigo.microservices.voucher.repository;

import com.codigo.microservices.voucher.entity.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, UUID> {
}
