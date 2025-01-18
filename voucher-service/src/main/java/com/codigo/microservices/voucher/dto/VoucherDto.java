package com.codigo.microservices.voucher.dto;

import com.codigo.microservices.voucher.entity.VoucherDiscount;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoucherDto {
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Expiry date is required")
    private LocalDate expiryDate;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amount;

    private List<VoucherDiscount> voucherDiscounts = new ArrayList<>();

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotBlank(message = "Buy type is required")
    private String buyType;

    @Pattern(regexp = "\\d{10,15}", message = "Phone number must be between 10 and 15 digits")
    @NotBlank(message = "Owner phone is required")
    private String ownerPhone;

    @NotBlank(message = "Status is required")
    private String status;
}
