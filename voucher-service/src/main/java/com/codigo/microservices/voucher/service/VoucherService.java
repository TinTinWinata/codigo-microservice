package com.codigo.microservices.voucher.service;

import com.codigo.microservices.voucher.dto.VoucherDto;
import com.codigo.microservices.voucher.entity.Voucher;
import com.codigo.microservices.voucher.enums.VoucherBuyType;
import com.codigo.microservices.voucher.enums.VoucherStatus;
import com.codigo.microservices.voucher.event.VoucherCreatedEvent;
import com.codigo.microservices.voucher.mapper.VoucherMapper;
import com.codigo.microservices.voucher.repository.PaymentMethodRepository;
import com.codigo.microservices.voucher.repository.VoucherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final KafkaTemplate<String, VoucherCreatedEvent> kafkaTemplate;
    private final PaymentMethodRepository paymentMethodRepository;

    public VoucherService(VoucherRepository voucherRepository, KafkaTemplate kafkaTemplate, PaymentMethodRepository paymentMethodRepository) {
        this.voucherRepository = voucherRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public Flux<VoucherDto> getAllVouchers(){
        return Flux.fromIterable(voucherRepository.findAll()).map(VoucherMapper::toDto);
    }

    private void createVoucherEvent(Voucher voucher){
        VoucherCreatedEvent voucherCreatedEvent = new VoucherCreatedEvent(voucher.getId().toString(), voucher.getQuantity());
        log.info("[Kafka Sent] - Sending Voucher Event {}.", voucher.getId());
        kafkaTemplate.send("voucher-created", voucherCreatedEvent);
        log.info("[Kafka Sent] - Voucher Event Sended {}.", voucher.getId());
    }

    public Mono<Voucher> createVoucher(VoucherDto voucherDto){
        return Mono.fromCallable(() -> paymentMethodRepository.findById(UUID.fromString(voucherDto.getPaymentMethodId()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid payment method ID")))
                .map(paymentMethod -> VoucherMapper.toEntity(voucherDto, paymentMethod))
                .flatMap(voucher -> Mono.fromCallable(() -> voucherRepository.save(voucher)))
                .doOnSuccess(this::createVoucherEvent);
    }

    public Mono<Voucher> getVoucherById(UUID id){
        return Mono.justOrEmpty(voucherRepository.findById(id));
    }

    public Mono<Void> deleteVoucher(UUID id){
        return getVoucherById(id)
                .flatMap(voucher -> {
                    voucherRepository.delete(voucher);
                    return Mono.empty();
                });
    }

    public Mono<Voucher> updateVoucher(UUID id, VoucherDto voucherDto) {
        return getVoucherById(id).flatMap(existingVoucher -> {
            existingVoucher.setTitle(voucherDto.getTitle());
            existingVoucher.setDescription(voucherDto.getDescription());
            existingVoucher.setExpiryDate(voucherDto.getExpiryDate());
            existingVoucher.setImageUrl(voucherDto.getImageUrl());
            existingVoucher.setAmount(voucherDto.getAmount());
            existingVoucher.setDiscount(voucherDto.getDiscount());
            existingVoucher.setQuantity(voucherDto.getQuantity());
            existingVoucher.setBuyType(VoucherBuyType.valueOf(voucherDto.getBuyType()));
//            existingVoucher.setPaymentMethodId(UUID.fromString(voucherDto.getPaymentMethodId()));
            existingVoucher.setOwnerPhone(voucherDto.getOwnerPhone());
            existingVoucher.setStatus(VoucherStatus.valueOf(voucherDto.getStatus()));
            return Mono.just(voucherRepository.save(existingVoucher));
        });
    }
}