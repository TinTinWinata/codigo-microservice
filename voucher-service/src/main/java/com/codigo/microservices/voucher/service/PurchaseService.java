package com.codigo.microservices.voucher.service;

import com.codigo.microservices.voucher.client.PromoCodeClient;
import com.codigo.microservices.voucher.dto.GetUnownedPromoCodeRequestDto;
import com.codigo.microservices.voucher.dto.PurchaseRequestDto;
import com.codigo.microservices.voucher.dto.PurchaseResponseDto;
import com.codigo.microservices.voucher.entity.PaymentMethod;
import com.codigo.microservices.voucher.entity.PurchaseHistory;
import com.codigo.microservices.voucher.entity.Voucher;
import com.codigo.microservices.voucher.repository.PurchaseHistoryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PurchaseService {

    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final VoucherService voucherService;
    private final PaymentMethodService paymentMethodService;
    private final PromoCodeClient promoCodeClient;

    public PurchaseService(PurchaseHistoryRepository purchaseHistoryRepository, VoucherService voucherService, PaymentMethodService paymentMethodService, PromoCodeClient promoCodeClient) {
        this.purchaseHistoryRepository = purchaseHistoryRepository;
        this.voucherService = voucherService;
        this.paymentMethodService = paymentMethodService;
        this.promoCodeClient = promoCodeClient;
    }

    public Mono<PurchaseResponseDto> purchaseVoucher(PurchaseRequestDto purchaseRequestDto) {
        return this.voucherService.getVoucherById(UUID.fromString(purchaseRequestDto.getVoucherId()))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid eVoucher ID")))
                .flatMap(voucher -> paymentMethodService.getPaymentMethodById(UUID.fromString(purchaseRequestDto.getPaymentMethodId()))
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid Payment Method ID")))
                        .flatMap(paymentMethod -> processPurchase(voucher, paymentMethod, purchaseRequestDto.getPhoneNumber(), purchaseRequestDto.getPaymentMetaId(), purchaseRequestDto.getQuantity()))
                );
    }


    private boolean checkPayment(PaymentMethod paymentMethod, String metaId) {
        // In a real-world implementation, we need to validate the purchase request with the third-party provider. For example, if the payment method is Visa, we need to verify whether the payment has been successfully processed and accepted using the associated (meta ID).
        return true;
    }

    private Mono<PurchaseResponseDto> processPurchase(Voucher voucher, PaymentMethod paymentMethod, String userPhone, String metaId, int voucherCount) {
        boolean paymentSuccessful = checkPayment(paymentMethod, metaId);

        if (!paymentSuccessful) {
            return Mono.error(new IllegalStateException("Invalid Payment"));
        }

        GetUnownedPromoCodeRequestDto unownedPromoCodeRequestDto = new GetUnownedPromoCodeRequestDto(voucher.getId().toString(), voucherCount, userPhone);

        return promoCodeClient.getUnownedPromoCodes(unownedPromoCodeRequestDto)
                .flatMap(promoCodeList -> {
                    PurchaseHistory purchaseHistory = PurchaseHistory.builder()
                            .userPhone(userPhone)
                            .voucher(voucher)
                            .purchaseDate(LocalDateTime.now())
                            .build();

                    return Mono.fromCallable(() -> this.purchaseHistoryRepository.save(purchaseHistory))
                            .map(history -> new PurchaseResponseDto("Purchase Successfully!", true, promoCodeList));
                });
    }
}
