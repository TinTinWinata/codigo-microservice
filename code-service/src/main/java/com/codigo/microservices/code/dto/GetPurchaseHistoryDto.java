package com.codigo.microservices.code.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class GetPurchaseHistoryDto {
    private List<UnusedPromoCodeDto> unusedPromoCodes;
    private List<String> usedPromoCodes;
}
