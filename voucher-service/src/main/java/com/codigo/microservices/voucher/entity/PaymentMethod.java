package com.codigo.microservices.voucher.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_methods")
public class PaymentMethod {
    @Id
    private Long id;

    @Column("name")
    private String name;
}
