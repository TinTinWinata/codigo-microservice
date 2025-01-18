package com.codigo.microservices.code.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPromoCodeStatusDto {
    private String code;
    private String status;
}
