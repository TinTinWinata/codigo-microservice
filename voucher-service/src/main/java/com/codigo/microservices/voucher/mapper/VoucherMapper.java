package com.codigo.microservices.voucher.mapper;

import com.codigo.microservices.voucher.dto.VoucherDto;
import com.codigo.microservices.voucher.entity.Voucher;
import com.codigo.microservices.voucher.entity.VoucherDiscount;

import java.util.ArrayList;
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
        voucher.setOwnerPhone(dto.getOwnerPhone());
        return voucher;
    }

    public static VoucherDto toDto(Voucher voucher) {
        return toDto(voucher, new ArrayList<>());
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
        dto.setOwnerPhone(voucher.getOwnerPhone());
        return dto;
    }
}
