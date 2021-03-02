package com.altimetrik.app.payment;

import java.math.BigDecimal;

public interface Payment {
    String getId();

    BigDecimal getAmount();

    String getCurrency();

    Integer getUserId();

    String getBankAccountNumber();
}
