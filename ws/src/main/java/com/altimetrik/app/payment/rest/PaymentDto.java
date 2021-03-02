package com.altimetrik.app.payment.rest;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
class PaymentDto {
    private String id;
    private BigDecimal amount;
    private String currency;
    private Integer userId;
    private String bankAccountNumber;
}
