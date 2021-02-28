package com.altimetrik.app.payment;

import java.util.List;

public interface PaymentRepository {

    List<Payment> findAll();

    void delete(int id);

    void save(Payment payment);

}
