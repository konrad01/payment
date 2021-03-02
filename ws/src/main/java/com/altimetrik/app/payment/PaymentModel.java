package com.altimetrik.app.payment;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PaymentModel {

    @CsvBindByPosition(position = 0)
    private String id;
    @CsvBindByPosition(position = 1)
    private BigDecimal amount;
    @CsvBindByPosition(position = 2)
    private String currency;
    @CsvBindByPosition(position = 3)
    private Integer userId;
    @CsvBindByPosition(position = 4)
    private String bankAccountNumber;
}
