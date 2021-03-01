package com.altimetrik.app.payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    Optional<Payment> get(int id);

    List<Payment> findAll();

    void addPayment(Payment payment);

    void remove(int id);

    void updatePayment(Payment payment);


}
