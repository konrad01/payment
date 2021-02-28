package com.altimetrik.app.payment;

import java.util.List;

public interface PaymentService {
    List<Payment> findAll();

    void addPayment(Payment payment);

    void remove(int id);

    void updatePayment(Payment payment);


}
