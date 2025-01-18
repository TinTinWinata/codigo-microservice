package com.codigo.microservices.code.repository;

import com.codigo.microservices.code.entity.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, String> {
    List<PromoCode> findByVoucherIdAndOwnerPhoneIsNull(String voucherId);
    List<PromoCode> findByOwnerPhone(String ownerPhone);
    PromoCode findByCode(String code);
    boolean existsByCode(String code);
}