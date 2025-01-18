package com.codigo.microservices.voucher.service;

import com.codigo.microservices.voucher.client.PromoCodeClient;
import com.codigo.microservices.voucher.dto.GetUnownedPromoCodeRequestDto;
import com.codigo.microservices.voucher.dto.PurchaseRequestDto;
import com.codigo.microservices.voucher.dto.PurchaseResponseDto;
import com.codigo.microservices.voucher.dto.UserDto;
import com.codigo.microservices.voucher.entity.PaymentMethod;
import com.codigo.microservices.voucher.entity.PurchaseHistory;
import com.codigo.microservices.voucher.entity.Voucher;
import com.codigo.microservices.voucher.enums.VoucherBuyType;
import com.codigo.microservices.voucher.enums.VoucherStatus;
import com.codigo.microservices.voucher.repository.PurchaseHistoryRepository;
import com.codigo.microservices.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PurchaseService {

    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final VoucherService voucherService;
    private final PaymentMethodService paymentMethodService;
    private final PromoCodeClient promoCodeClient;
    private final VoucherRepository voucherRepository;

    public PurchaseService(PurchaseHistoryRepository purchaseHistoryRepository, VoucherService voucherService, PaymentMethodService paymentMethodService, PromoCodeClient promoCodeClient, VoucherRepository voucherRepository) {
        this.purchaseHistoryRepository = purchaseHistoryRepository;
        this.voucherService = voucherService;
        this.paymentMethodService = paymentMethodService;
        this.promoCodeClient = promoCodeClient;
        this.voucherRepository = voucherRepository;
    }

    public Mono<PurchaseResponseDto> purchaseVoucher(UserDto userDto, PurchaseRequestDto purchaseRequestDto) {
        return this.voucherService.getVoucherById(purchaseRequestDto.getVoucherId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid eVoucher ID")))
                .flatMap(voucher ->
                        {

                            return paymentMethodService.getPaymentMethodById(purchaseRequestDto.getPaymentMethodId())
                                .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid Payment Method ID")))
                                .flatMap(paymentMethod -> processPurchase(voucher, paymentMethod, userDto, purchaseRequestDto));
                        }
                    );
    }


    private boolean checkPayment(PaymentMethod paymentMethod, String metaId) {
        // In a real-world implementation, we need to validate the purchase request with the third-party provider. For example, if the payment method is Visa, we need to verify whether the payment has been successfully processed and accepted using the associated (meta ID).
        return true;
    }


    private UserDto checkPurchaseUser(Voucher voucher, UserDto user, PurchaseRequestDto purchaseRequestDto){
        if(voucher.getBuyType() == VoucherBuyType.GIFT_TO_OTHERS) {
            return UserDto.builder().name(purchaseRequestDto.getUserName()).phoneNumber(purchaseRequestDto.getPhoneNumber()).build();
        }
        return user;
    }
    private Mono<PurchaseResponseDto> processPurchase(Voucher voucher, PaymentMethod paymentMethod, UserDto user, PurchaseRequestDto purchaseRequestDto) {
        if(voucher.getStatus() == VoucherStatus.INACTIVE){
            return Mono.error(new IllegalStateException("Voucher is inactive"));
        }

        boolean paymentSuccessful = checkPayment(paymentMethod, purchaseRequestDto.getPaymentMetaId());

        if (!paymentSuccessful) {
            return Mono.error(new IllegalStateException("Invalid Payment"));
        }

        if(voucher.getQuantity() < purchaseRequestDto.getQuantity()){
            return Mono.error(new IllegalStateException("Invalid Voucher Quantity"));
        }

        List<PurchaseHistory> purchaseHistories = purchaseHistoryRepository.findByUserPhoneAndVoucherId(user.getPhoneNumber(), voucher.getId());
        if(purchaseHistories.size() > voucher.getMaxBuyLimit()) {
            return Mono.error(new IllegalStateException("You have already purchased the maximum number of eVouchers."));
        }

        UserDto toUser = checkPurchaseUser(voucher, user, purchaseRequestDto);

        if(voucher.getBuyType() == VoucherBuyType.GIFT_TO_OTHERS) {
            List<PurchaseHistory> toPurchaseHistories = purchaseHistoryRepository.findByToPhoneAndVoucherId(toUser.getPhoneNumber(), voucher.getId());
            if(toPurchaseHistories.size() > voucher.getMaxUserLimitFromGift()) {
                return Mono.error(new IllegalStateException("The person already has the maximum number of eVouchers from gift"));
            }
        }

        GetUnownedPromoCodeRequestDto unownedPromoCodeRequestDto = new GetUnownedPromoCodeRequestDto(voucher.getId(), purchaseRequestDto.getQuantity(), user.getPhoneNumber());

        return promoCodeClient.getUnownedPromoCodes(unownedPromoCodeRequestDto)
                .flatMap(promoCodeList -> {
                    PurchaseHistory purchaseHistory = PurchaseHistory.builder()
                            .toPhone(user.getPhoneNumber())
                            .toName(user.getName())
                            .voucherId(voucher.getId())
                            .purchaseDate(LocalDateTime.now())
                            .build();

                    return this.purchaseHistoryRepository.save(purchaseHistory)
                            .map(history -> new PurchaseResponseDto("Purchase Successfully!", true, promoCodeList));
                });
    }
}
