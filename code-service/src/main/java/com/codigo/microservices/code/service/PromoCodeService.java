package com.codigo.microservices.code.service;

import com.codigo.microservices.code.constant.PropertyConstant;
import com.codigo.microservices.code.dto.*;
import com.codigo.microservices.code.entity.PromoCode;
import com.codigo.microservices.code.enums.VoucherStatus;
import com.codigo.microservices.voucher.event.VoucherCreatedEvent;
import com.codigo.microservices.code.repository.PromoCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class PromoCodeService {
    private final PromoCodeRepository promoCodeRepository;
    private final QRCodeService qrCodeService;

    public PromoCodeService(PromoCodeRepository promoCodeRepository, QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
        this.promoCodeRepository = promoCodeRepository;
    }

    private String GenerateCode(){
        StringBuilder promoCode = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < PropertyConstant.PROMO_CODE_DIGITS_COUNT; i++) {
            promoCode.append(PropertyConstant.PROMO_CODE_DIGITS
                    .charAt(random.nextInt(PropertyConstant.PROMO_CODE_DIGITS.length())));
        }

        for (int i = 0; i < PropertyConstant.PROMO_CODE_ALPHABETS_COUNT; i++) {
            promoCode.append(PropertyConstant.PROMO_CODE_ALPHABETS
                    .charAt(random.nextInt(PropertyConstant.PROMO_CODE_ALPHABETS.length())));
        }

        return promoCode.toString();
    }

    private String getNextCode(){
        String promoCode = GenerateCode();
        while (promoCodeRepository.existsByCode(promoCode)){
            promoCode = GenerateCode();
        }
        return promoCode;
    }

    @KafkaListener(topics = "voucher-created")
    public void voucherCreated(VoucherCreatedEvent event) {
        log.info("[Kafka Listened] Voucher created: {}", event);
        int quantity = event.getQuantity();
        for(int i = 0; i < quantity; i++){
            String voucherId = event.getVoucherId().toString();
            String generatedCode = this.getNextCode();
            PromoCode promoCode = PromoCode.builder()
                    .code(generatedCode)
                    .voucherId(voucherId)
                    .status(VoucherStatus.ACTIVE)
                    .generatedDate(LocalDateTime.now())
                    .qrCode(this.qrCodeService.generateQRCode(generatedCode, "qr-" + generatedCode))
                    .build();
            promoCodeRepository.save(promoCode);
        }
    }

    public Mono<PromoCode> updateStatusPromoCode(UpdatePromoCodeStatusDto updatePromoCodeStatusDto) {
        return Mono.fromCallable(() -> {
            PromoCode promoCode = promoCodeRepository.findByCode(updatePromoCodeStatusDto.getCode());
            promoCode.setStatus(VoucherStatus.USED);
            return promoCodeRepository.save(promoCode);
        });
    }

    public Mono<GetPurchaseHistoryDto> getPurchaseHistoryByPhone(String phoneNumber) {
        return Mono.fromCallable(() -> {
            List<PromoCode> promoCodes = promoCodeRepository.findByOwnerPhone(phoneNumber);
            GetPurchaseHistoryDto purchaseHistoryDto = new GetPurchaseHistoryDto(new ArrayList<UnusedPromoCodeDto>(), new ArrayList<String>());
            for (PromoCode promoCode : promoCodes) {
                var status = promoCode.getStatus();
                if(status == VoucherStatus.ACTIVE){
                    purchaseHistoryDto.getUnusedPromoCodes().add(UnusedPromoCodeDto.builder().qrCodeImages(promoCode.getQrCode()).promoCode(promoCode.getCode()).build());
                }
                if(status == VoucherStatus.USED){
                    purchaseHistoryDto.getUsedPromoCodes().add(promoCode.getCode());
                }
            }
            return purchaseHistoryDto;
        });
    }

    public Mono<GetPromoCodeStatusDto> getPromoCodeStatusByCode(String code) {
        return Mono.fromCallable(() -> {
                PromoCode promoCode = promoCodeRepository.findByCode(code);
                GetPromoCodeStatusDto getPromoCodeStatusDto = GetPromoCodeStatusDto
                        .builder()
                        .code(code)
                        .status(promoCode.getStatus().toString())
                        .build();
                return getPromoCodeStatusDto;
        });
    }


    public Flux<PromoCode> getUnownedPromoCodes(GetUnownedPromoCodeRequestDto getUnownedPromoCodeRequestDto) {
        return Flux.fromIterable(promoCodeRepository.findByVoucherIdAndOwnerPhoneIsNull(getUnownedPromoCodeRequestDto.getVoucherId()))
                .take(getUnownedPromoCodeRequestDto.getVoucherCount())
                .flatMap(
                        promoCode -> {
                            promoCode.setOwnerPhone(getUnownedPromoCodeRequestDto.getPhoneNumber());
                            return Mono.just(promoCodeRepository.save(promoCode));
                        }
                );
    }
}
