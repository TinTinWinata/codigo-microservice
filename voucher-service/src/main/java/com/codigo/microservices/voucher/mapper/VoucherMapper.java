package com.codigo.microservices.voucher.mapper;

import com.codigo.microservices.voucher.dto.VoucherDto;
import com.codigo.microservices.voucher.entity.Voucher;
import com.codigo.microservices.voucher.entity.VoucherDiscount;
import com.codigo.microservices.voucher.enums.VoucherBuyType;

import java.util.List;


public class VoucherMapper {
    public static Voucher toEntity(VoucherDto dto) {
        Voucher voucher = new Voucher();
        voucher.setTitle(dto.getTitle());
        voucher.setDescription(dto.getDescription());
        voucher.setExpiryDate(dto.getExpiryDate());
        voucher.setImageUrl(dto.getImageUrl());
        voucher.setAmount(dto.getAmount());
        voucher.setQuantity(dto.getQuantity());
        voucher.setBuyType(VoucherBuyType.valueOf(dto.getBuyType()));
        voucher.setMaxBuyLimit(dto.getMaxBuyLimit());
        voucher.setMaxUserLimitFromGift(dto.getMaxUserLimitFromGift());
        return voucher;
    }

    public static VoucherDto toDto(Voucher voucher) {
        return toDto(voucher, null);
    }

    public static VoucherDto toDto(Voucher voucher, List<VoucherDiscount> voucherDiscounts) {
        VoucherDto dto = new VoucherDto();
        dto.setId(voucher.getId());
        dto.setTitle(voucher.getTitle());
        dto.setDescription(voucher.getDescription());
        dto.setExpiryDate(voucher.getExpiryDate());
        dto.setImageUrl(voucher.getImageUrl());
        dto.setAmount(voucher.getAmount());
        dto.setVoucherDiscounts(voucherDiscounts);
        dto.setQuantity(voucher.getQuantity());
        dto.setBuyType(voucher.getBuyType().name());
        dto.setStatus(voucher.getStatus().name());
        dto.setMaxBuyLimit(voucher.getMaxBuyLimit());
        dto.setMaxUserLimitFromGift(voucher.getMaxUserLimitFromGift());
        return dto;
    }
}
