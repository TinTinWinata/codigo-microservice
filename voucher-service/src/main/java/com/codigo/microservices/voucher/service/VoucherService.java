package com.codigo.microservices.voucher.service;

import com.codigo.microservices.voucher.dto.VoucherDiscountDto;
import com.codigo.microservices.voucher.dto.VoucherDto;
import com.codigo.microservices.voucher.entity.Voucher;
import com.codigo.microservices.voucher.entity.VoucherDiscount;
import com.codigo.microservices.voucher.event.VoucherCreatedEvent;
import com.codigo.microservices.voucher.mapper.VoucherMapper;
import com.codigo.microservices.voucher.repository.VoucherDiscountRepository;
import com.codigo.microservices.voucher.repository.VoucherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final KafkaTemplate<String, VoucherCreatedEvent> kafkaTemplate;
    private final PaymentMethodService paymentMethodService;
    private final VoucherDiscountRepository voucherDiscountRepository;

    public VoucherService(VoucherRepository voucherRepository, KafkaTemplate kafkaTemplate, PaymentMethodService paymentMethodService, VoucherDiscountRepository voucherDiscountRepository) {
        this.voucherRepository = voucherRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.paymentMethodService = paymentMethodService;
        this.voucherDiscountRepository = voucherDiscountRepository;
    }

    public Flux<VoucherDto> getAllVouchers(){
        return voucherRepository.findAll().map(VoucherMapper::toDto);
    }

    private void createVoucherEvent(Voucher voucher){
        VoucherCreatedEvent voucherCreatedEvent = new VoucherCreatedEvent(voucher.getId().toString(), voucher.getQuantity());
        log.info("[Kafka Sent] - Sending Voucher Event {}.", voucher.getId());
        kafkaTemplate.send("voucher-created", voucherCreatedEvent);
        log.info("[Kafka Sent] - Voucher Event Sended {}.", voucher.getId());
    }


    public Mono<VoucherDiscount> createVoucherDiscount(VoucherDiscountDto voucherDiscountDto) {
        VoucherDiscount voucherDiscount = VoucherDiscount.builder()
                .voucherId(voucherDiscountDto.getVoucherId())
                .paymentMethodId(voucherDiscountDto.getPaymentMethodId())
                .discount(voucherDiscountDto.getDiscount())
                .discountDescription(voucherDiscountDto.getDiscountDescription())
                .build();

        return voucherDiscountRepository.save(voucherDiscount);
    }

    public Mono<VoucherDto> getVoucherByIdWithDiscount(Long voucherId){
        Mono<Voucher> voucherMono = voucherRepository.findById(voucherId);
        Flux<VoucherDiscount> discountFlux = voucherDiscountRepository.findByVoucherId(voucherId);
        return voucherMono.zipWith(discountFlux.collectList())
                .map(tuple -> {
                    Voucher voucher = tuple.getT1();
                    List<VoucherDiscount> discounts = tuple.getT2();
                    return VoucherMapper.toDto(voucher, discounts);
                });
    }

    public Mono<Voucher> createVoucher(VoucherDto voucherDto){
        Voucher voucher = VoucherMapper.toEntity(voucherDto);
        return voucherRepository.save(voucher).doOnSuccess(this::createVoucherEvent);
    }

    public Mono<Voucher> getVoucherById(Long id){
        return voucherRepository.findById(id);
    }

    public Mono<Void> deleteVoucher(Long id){
        return getVoucherById(id)
                .flatMap(voucher -> {
                    voucherRepository.delete(voucher);
                    return Mono.empty();
                });
    }

    public Mono<Voucher> updateVoucher(Long id, VoucherDto voucherDto) {
        return getVoucherById(id).flatMap(existingVoucher -> {
            existingVoucher.setTitle(voucherDto.getTitle());
            existingVoucher.setDescription(voucherDto.getDescription());
            existingVoucher.setExpiryDate(voucherDto.getExpiryDate());
            existingVoucher.setImageUrl(voucherDto.getImageUrl());
            existingVoucher.setAmount(voucherDto.getAmount());
            existingVoucher.setQuantity(voucherDto.getQuantity());
            existingVoucher.setOwnerPhone(voucherDto.getOwnerPhone());
            return voucherRepository.save(existingVoucher);
        });
    }
}