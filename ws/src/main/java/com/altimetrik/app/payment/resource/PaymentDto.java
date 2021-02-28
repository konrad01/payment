package com.altimetrik.app.payment.resource;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDto {
    private Integer id;
    private BigDecimal amount;
    private String currency;
    private Integer userId;
    private String bankAccountNumber;
}
