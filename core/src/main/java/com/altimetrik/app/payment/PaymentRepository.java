package com.altimetrik.app.payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    Optional<Payment> findById(String id);

    List<Payment> findAll();

    void delete(String id);

    String save(Payment payment);

    void update(String id, Payment payment);

}
