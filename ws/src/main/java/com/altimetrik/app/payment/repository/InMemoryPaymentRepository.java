package com.altimetrik.app.payment.repository;

import com.altimetrik.app.payment.Payment;
import com.altimetrik.app.payment.PaymentRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;

@Slf4j
class InMemoryPaymentRepository implements PaymentRepository {
    @Override
    public Optional<Payment> findById(int id) {
        log.warn("Operation findById not implemented yet for InMemoryRepository");
        return empty();
    }

    @Override
    public List<Payment> findAll() {
        log.warn("Operation findAll not yet implemented for InMemoryRepository");
        return emptyList();
    }

    @Override
    public void save(Payment payment) {
        log.warn("Operation save not yet implemented for InMemoryRepository");
    }

    @Override
    public void delete(int id) {
        log.warn("Operation delete not yet implemented for InMemoryRepository");
    }
}
