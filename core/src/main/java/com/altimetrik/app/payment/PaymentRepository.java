package com.altimetrik.app.payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    Optional<Payment> findById(int id);

    List<Payment> findAll();

    void delete(int id);

    void save(Payment payment);

}
