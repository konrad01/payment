package com.altimetrik.app.payment;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;

@Slf4j
class InMemoryPaymentRepository implements PaymentRepository {
    @Override
    public Optional<Payment> findById(String id) {
        log.warn("Operation findById not implemented yet for InMemoryRepository");
        return empty();
    }

    @Override
    public List<Payment> findAll() {
        log.warn("Operation findAll not yet implemented for InMemoryRepository");
        return emptyList();
    }

    @Override
    public String save(Payment payment) {
        log.warn("Operation save not yet implemented for InMemoryRepository");
        return null;
    }

    @Override
    public void update(String id, Payment payment) {
        log.warn("Operation update not yet implemented for InMemoryRepository");
    }

    @Override
    public void delete(String id) {
        log.warn("Operation delete not yet implemented for InMemoryRepository");
    }
}
