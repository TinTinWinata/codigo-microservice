package com.codigo.microservices.voucher.repository;

import com.codigo.microservices.voucher.entity.PaymentMethod;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends R2dbcRepository<PaymentMethod, Long> {
}
