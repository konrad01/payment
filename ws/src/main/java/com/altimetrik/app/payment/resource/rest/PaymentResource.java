package com.altimetrik.app.payment.resource.rest;

import com.altimetrik.app.exceptions.ResourceNotFoundException;
import com.altimetrik.app.payment.Payment;
import com.altimetrik.app.payment.PaymentService;
import com.altimetrik.app.payment.resource.PaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;


@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentResource {
    private final PaymentService paymentService;

    @GetMapping("/payments/{id}")
    public PaymentDto getPayment(@PathVariable Integer id) {
        return paymentService.get(id)
                .map(this::toDto)
                .orElseThrow(
                        () -> new ResourceNotFoundException(format("payment not found for id = %s", id)));
    }

    @GetMapping("/payments")
    public List<PaymentDto> getPayments() {
        return paymentService.findAll().stream()
                .map(this::toDto)
                .collect(toList());
    }

    @PostMapping("/payments")
    public void create(@Valid @RequestBody PaymentDto paymentDto) {
        paymentService.addPayment(new PaymentAdapter(paymentDto));
    }

    @PutMapping("/payments")
    public void update(@RequestBody PaymentDto paymentDto) {
        paymentService.updatePayment(new PaymentAdapter(paymentDto));
    }

    @DeleteMapping("/payments/{id}")
    public void delete(@PathVariable Integer id) {
        paymentService.remove(id);
    }

    private PaymentDto toDto(Payment payment) {
        PaymentDto dto = new PaymentDto();
        dto.setId(payment.getId());
        dto.setAmount(payment.getAmount());
        dto.setCurrency(payment.getCurrency());
        dto.setUserId(payment.getUserId());
        dto.setBankAccountNumber(payment.getBankAccountNumber());

        return dto;
    }

    @RequiredArgsConstructor
    private static class PaymentAdapter implements Payment {
        private final PaymentDto dto;

        @Override
        public Integer getId() {
            return dto.getId();
        }

        @Override
        public BigDecimal getAmount() {
            return dto.getAmount();
        }

        @Override
        public String getCurrency() {
            return dto.getCurrency();
        }

        @Override
        public Integer getUserId() {
            return dto.getUserId();
        }

        @Override
        public String getBankAccountNumber() {
            return dto.getBankAccountNumber();
        }
    }
}
