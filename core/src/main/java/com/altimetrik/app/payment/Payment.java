package com.altimetrik.app.payment;

import java.math.BigDecimal;

public interface Payment {
    Integer getId();

    BigDecimal getAmount();

    String getCurrency();

    Integer getUserId();

    String getBankAccountNumber();
}
