package com.codigo.microservices.voucher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromoCodeDto {
    private String id;
    private String code;
    private String qrCode;
    private String voucherId;
    private String ownerPhone;
    private boolean isUsed;
    private LocalDateTime generatedDate;
    private LocalDateTime boughtDate;
}
