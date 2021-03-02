package com.altimetrik.app.payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    Optional<Payment> get(String id);

    List<Payment> findAll();

    String addPayment(Payment payment);

    void remove(String id);

    void update(String id, Payment payment);


}
