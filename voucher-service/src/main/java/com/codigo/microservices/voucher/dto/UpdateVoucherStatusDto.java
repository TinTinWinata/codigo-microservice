package com.codigo.microservices.voucher.dto;

import com.codigo.microservices.voucher.enums.VoucherStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVoucherStatusDto {
    private VoucherStatus voucherStatus;
}
