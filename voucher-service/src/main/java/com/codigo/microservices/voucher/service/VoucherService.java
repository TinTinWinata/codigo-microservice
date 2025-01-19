package com.codigo.microservices.voucher.service;

import com.codigo.microservices.voucher.constant.PropertyConstant;
import com.codigo.microservices.voucher.dto.*;
import com.codigo.microservices.voucher.entity.Voucher;
import com.codigo.microservices.voucher.entity.VoucherDiscount;
import com.codigo.microservices.voucher.enums.VoucherBuyType;
import com.codigo.microservices.voucher.event.VoucherCreatedEvent;
import com.codigo.microservices.voucher.mapper.VoucherMapper;
import com.codigo.microservices.voucher.repository.VoucherDiscountRepository;
import com.codigo.microservices.voucher.repository.VoucherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final KafkaTemplate<String, VoucherCreatedEvent> kafkaTemplate;
    private final VoucherDiscountRepository voucherDiscountRepository;
    private final ReactiveRedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public VoucherService(VoucherRepository voucherRepository, KafkaTemplate kafkaTemplate, VoucherDiscountRepository voucherDiscountRepository, ReactiveRedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
        this.voucherRepository = voucherRepository;
        this.kafkaTemplate = kafkaTemplate;
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

    public Mono<VoucherDto> createVoucherDiscount(VoucherDiscountDto voucherDiscountDto) {
        VoucherDiscount voucherDiscount = VoucherDiscount.builder()
                .voucherId(voucherDiscountDto.getVoucherId())
                .paymentMethodId(voucherDiscountDto.getPaymentMethodId())
                .discount(voucherDiscountDto.getDiscount())
                .discountDescription(voucherDiscountDto.getDiscountDescription())
                .build();

        return voucherDiscountRepository.save(voucherDiscount)
                .then(fetchAndCacheVoucher(voucherDiscount.getVoucherId()))
                .onErrorResume(ex -> Mono.error(new IllegalArgumentException("Error while saving, please check Payment Method & Voucher is Exists!", ex)));
    }

    public Mono<VoucherDto> getVoucherByIdWithDiscount(Long voucherId){
        return getCacheVoucherDto(voucherId)
                .switchIfEmpty(fetchAndCacheVoucher(voucherId));
    }

    private Mono<VoucherDto> fetchAndCacheVoucher(Long voucherId) {
        return voucherRepository.findById(voucherId)
                .zipWith(voucherDiscountRepository.findByVoucherId(voucherId).collectList())
                .flatMap(tuple -> {
                    Voucher voucher = tuple.getT1();
                    List<VoucherDiscount> discounts = tuple.getT2();
                    VoucherDto dto = VoucherMapper.toDto(voucher, discounts);
                    return setVoucherDtoCache(voucherId, dto).thenReturn(dto);
                })
                .doOnError(ex -> log.error("Error fetching voucher {}: {}", voucherId, ex.getMessage(), ex));
    }

    public Mono<Voucher> createVoucher(VoucherDto voucherDto){
        Voucher voucher = VoucherMapper.toEntity(voucherDto);
        return voucherRepository.save(voucher).doOnSuccess(this::createVoucherEvent).doOnSuccess(object -> fetchAndCacheVoucher(object.getId()));
    }

    public Mono<VoucherDto> updateVoucherStatus(Long voucherId, UpdateVoucherStatusDto updateVoucherStatusDto){
        return this.getVoucherById(voucherId).flatMap(voucher -> {
            voucher.setStatus(updateVoucherStatusDto.getVoucherStatus());
            return voucherRepository.save(voucher).then(fetchAndCacheVoucher(voucherId));
        });
    }

    public Mono<ResponseCheckoutPriceDto> getCheckoutPrice(RequestCheckoutPriceDto requestCheckoutPriceDto) {
        return this.getVoucherByIdWithDiscount(requestCheckoutPriceDto.getVoucherId())
                .map(voucherDto -> {
                    BigDecimal discount = voucherDto.getVoucherDiscounts().stream()
                            .filter(voucherDiscount -> requestCheckoutPriceDto.getPaymentMethodId().equals(voucherDiscount.getPaymentMethodId()))
                            .map(VoucherDiscount::getDiscount)
                            .findFirst()
                            .orElse(BigDecimal.ZERO);

                    BigDecimal voucherPrice = voucherDto.getAmount();
                    BigDecimal totalPrice =  voucherPrice.subtract(
                            voucherPrice.multiply(discount.divide(BigDecimal.valueOf(100)))
                    ).multiply(requestCheckoutPriceDto.getQuantity());

                    return ResponseCheckoutPriceDto.builder()
                            .voucherPrice(voucherPrice)
                            .totalPrice(totalPrice)
                            .discount(discount)
                            .build();
                });
    }


    public Mono<Voucher> getVoucherById(Long id){
        return voucherRepository.findById(id);
    }

    private Mono<VoucherDto> getCacheVoucherDto(Long voucherId) {
        String cacheKey = buildCacheKey(voucherId);
        return redisTemplate.opsForValue().get(cacheKey)
                .flatMap(object -> {
                    try {
                        VoucherDto voucherDto = objectMapper.convertValue(object, VoucherDto.class);
                        return Mono.just(voucherDto);
                    } catch (Exception e) {
                        log.error("Error converting cached object to VoucherDto for key {}: {}", cacheKey, e.getMessage());
                        return Mono.empty();
                    }
                });
    }

    public Mono<Void> deleteVoucher(Long id){
        return getVoucherById(id)
                .flatMap(voucher ->voucherRepository.delete(voucher)
                        .then(clearVoucherDtoCache(id)));
    }

    private Mono<Void> setVoucherDtoCache(Long voucherId, VoucherDto value) {
        String cacheKey = buildCacheKey(voucherId);
        return redisTemplate.opsForValue().set(cacheKey, value)
                .doOnSuccess(success -> log.info("Cache updated for key: {}", cacheKey))
                .doOnError(error -> log.error("Failed to update cache for key: {}", cacheKey, error))
                .then();
    }

    private Mono<Void> clearVoucherDtoCache(Long voucherId) {
        String cacheKey = buildCacheKey(voucherId);
        return redisTemplate.delete(cacheKey)
                .doOnSuccess(success -> log.info("Cache cleared for voucherId: {}", voucherId))
                .doOnError(ex -> log.error("Error clearing cache for voucherId {}: {}", voucherId, ex.getMessage(), ex))
                .then();
    }

    private String buildCacheKey(Long voucherId) {
        return PropertyConstant.REDIS_VOUCHER_KEY + voucherId;
    }

    public Mono<VoucherDto> updateVoucher(Long id, VoucherDto voucherDto) {
        return getVoucherById(id).flatMap(existingVoucher -> {
            existingVoucher.setTitle(voucherDto.getTitle());
            existingVoucher.setDescription(voucherDto.getDescription());
            existingVoucher.setExpiryDate(voucherDto.getExpiryDate());
            existingVoucher.setImageUrl(voucherDto.getImageUrl());
            existingVoucher.setAmount(voucherDto.getAmount());
            existingVoucher.setQuantity(voucherDto.getQuantity());
            existingVoucher.setMaxUserLimitFromGift(voucherDto.getMaxUserLimitFromGift());
            existingVoucher.setMaxBuyLimit(voucherDto.getMaxBuyLimit());
            existingVoucher.setBuyType(VoucherBuyType.valueOf(voucherDto.getBuyType()));
            return voucherRepository.save(existingVoucher).then(fetchAndCacheVoucher(id));
        });
    }
}