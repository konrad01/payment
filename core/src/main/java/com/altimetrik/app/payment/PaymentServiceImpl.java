package com.altimetrik.app.payment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public void addPayment(Payment payment) {
        paymentRepository.save(payment);
    }

    public void remove(int id) {
        paymentRepository.delete(id);
    }

    public void updatePayment(Payment payment) {
        paymentRepository.save(payment);
    }
}
