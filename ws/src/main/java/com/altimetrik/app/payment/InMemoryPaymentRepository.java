package com.altimetrik.app.payment;

import java.util.List;

public class InMemoryPaymentRepository implements PaymentRepository {
    @Override
    public List<Payment> findAll() {
        throw new UnsupportedOperationException("Operation not supported for InMemoryRepository");
    }

    @Override
    public void save(Payment payment) {
        throw new UnsupportedOperationException("Operation not supported for InMemoryRepository");
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Operation not supported for InMemoryRepository");
    }
}
