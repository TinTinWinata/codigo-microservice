package com.codigo.microservices.voucher.mapper;

import com.codigo.microservices.voucher.dto.VoucherDto;
import com.codigo.microservices.voucher.entity.PaymentMethod;
import com.codigo.microservices.voucher.entity.Voucher;
import com.codigo.microservices.voucher.entity.VoucherDiscount;
import com.codigo.microservices.voucher.enums.VoucherBuyType;
import com.codigo.microservices.voucher.enums.VoucherStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class VoucherMapper {
    public static Voucher toEntity(VoucherDto dto) {
        return toEntity(dto, new ArrayList<>());
    }
    public static Voucher toEntity(VoucherDto dto, List<VoucherDiscount> voucherDiscounts) {
        Voucher voucher = new Voucher();
        voucher.setTitle(dto.getTitle());
        voucher.setDescription(dto.getDescription());
        voucher.setExpiryDate(dto.getExpiryDate());
        voucher.setImageUrl(dto.getImageUrl());
        voucher.setVoucherDiscounts(voucherDiscounts);
        voucher.setAmount(dto.getAmount());
        voucher.setQuantity(dto.getQuantity());
        voucher.setBuyType(VoucherBuyType.valueOf(dto.getBuyType()));
        voucher.setOwnerPhone(dto.getOwnerPhone());
        voucher.setStatus(VoucherStatus.valueOf(dto.getStatus()));
        return voucher;
    }

    public static VoucherDto toDto(Voucher voucher) {
        VoucherDto dto = new VoucherDto();
        dto.setId(voucher.getId().toString());
        dto.setTitle(voucher.getTitle());
        dto.setDescription(voucher.getDescription());
        dto.setExpiryDate(voucher.getExpiryDate());
        dto.setImageUrl(voucher.getImageUrl());
        dto.setAmount(voucher.getAmount());
        dto.setVoucherDiscounts(voucher.getVoucherDiscounts().stream().map(discount -> discount.getId().toString()).collect(Collectors.toList()));
        dto.setQuantity(voucher.getQuantity());
        dto.setBuyType(voucher.getBuyType().toString());
        dto.setOwnerPhone(voucher.getOwnerPhone());
        dto.setStatus(voucher.getStatus().toString());
        return dto;
    }
}
