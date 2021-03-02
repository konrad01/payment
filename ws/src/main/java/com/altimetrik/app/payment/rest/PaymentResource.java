package com.altimetrik.app.payment.rest;

import com.altimetrik.app.payment.Payment;
import com.altimetrik.app.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;


@RestController
@RequiredArgsConstructor
public class PaymentResource {
    private final PaymentService paymentService;

    @GetMapping("/payments/{id}")
    public PaymentDto getPayment(@PathVariable String id) {
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
    public MessageResponse create(@RequestBody PaymentForm form) {
        String id = paymentService.addPayment(new PaymentAdapter(form));

        return MessageResponse.ok(id);
    }

    @PutMapping("/payments/{id}")
    public HttpStatus update(@PathVariable String id, @RequestBody PaymentForm form) {
        paymentService.update(id, new PaymentAdapter(form));

        return HttpStatus.OK;
    }

    @DeleteMapping("/payments/{id}")
    public HttpStatus delete(@PathVariable String id) {
        paymentService.remove(id);

        return HttpStatus.OK;
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
        private final PaymentForm form;

        @Override
        public String getId() {
            return null;
        }

        @Override
        public BigDecimal getAmount() {
            return form.getAmount();
        }

        @Override
        public String getCurrency() {
            return form.getCurrency();
        }

        @Override
        public Integer getUserId() {
            return form.getUserId();
        }

        @Override
        public String getBankAccountNumber() {
            return form.getBankAccountNumber();
        }
    }
}
