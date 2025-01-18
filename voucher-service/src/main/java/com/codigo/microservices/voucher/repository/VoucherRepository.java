package com.codigo.microservices.voucher.repository;

import com.codigo.microservices.voucher.entity.Voucher;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends R2dbcRepository<Voucher, Long> {
}
