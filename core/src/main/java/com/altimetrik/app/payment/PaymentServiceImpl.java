package com.altimetrik.app.payment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public Optional<Payment> get(String id) {
        log.debug("searching for payment with id = {} ", id);
        return paymentRepository.findById(id);
    }

    public List<Payment> findAll() {
        log.debug("fetching payments");
        return paymentRepository.findAll();
    }

    public String addPayment(Payment payment) {
        log.debug("creating new payment with id = {} ", payment.getId());
        return paymentRepository.save(payment);
    }

    public void remove(String id) {
        log.debug("deleting payment for id = {} ", id);
        paymentRepository.delete(id);
    }

    public void update(String id, Payment payment) {
        log.info("updating payment for id = {} ", id);
        paymentRepository.update(id, payment);
    }
}
