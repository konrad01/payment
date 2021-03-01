package com.altimetrik.app.payment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public Optional<Payment> get(int id) {
        log.info(format("searching for payment with id = {%s} ", id));
        return paymentRepository.findById(id);
    }

    public List<Payment> findAll() {
        log.info("fetching payments");
        return paymentRepository.findAll();
    }

    public void addPayment(Payment payment) {
        log.info(format("creating new payment with id = {%s} ", payment.getId()));
        paymentRepository.save(payment);
    }

    public void remove(int id) {
        log.info(format("deleting payment for id = {%s} ", id));
        paymentRepository.delete(id);
    }

    public void updatePayment(Payment payment) {
        log.info(format("updating payment for id = {%s} ", payment.getId()));
        paymentRepository.save(payment);
    }
}
