package com.codigo.microservices.code.dto;

import com.codigo.microservices.code.enums.VoucherStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePromoCodeStatusDto {
    private String code;
    private VoucherStatus status;
}
