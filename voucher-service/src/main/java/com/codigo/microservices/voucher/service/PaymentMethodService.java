package com.codigo.microservices.voucher.service;

import com.codigo.microservices.voucher.dto.VoucherDto;
import com.codigo.microservices.voucher.entity.PaymentMethod;
import com.codigo.microservices.voucher.entity.Voucher;
import com.codigo.microservices.voucher.enums.VoucherBuyType;
import com.codigo.microservices.voucher.enums.VoucherStatus;
import com.codigo.microservices.voucher.mapper.VoucherMapper;
import com.codigo.microservices.voucher.repository.PaymentMethodRepository;
import com.codigo.microservices.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public Mono<PaymentMethod> getPaymentMethodById(UUID id){
        return Mono.justOrEmpty(paymentMethodRepository.findById(id));
    }

    public Flux<PaymentMethod> getAllPaymentMethods(){
        return Flux.fromIterable(paymentMethodRepository.findAll());
    }
}