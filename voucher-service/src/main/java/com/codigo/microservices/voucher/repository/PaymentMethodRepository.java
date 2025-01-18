package com.codigo.microservices.voucher.repository;

import com.codigo.microservices.voucher.entity.PaymentMethod;
import com.codigo.microservices.voucher.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID> {
}
