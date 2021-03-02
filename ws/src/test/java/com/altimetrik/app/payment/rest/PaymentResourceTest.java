package com.altimetrik.app.payment.rest;

import com.altimetrik.app.payment.Payment;
import com.altimetrik.app.payment.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

class PaymentResourceTest {
    private final PaymentService paymentService = mock(PaymentService.class);
    private final PaymentResource paymentResource = new PaymentResource(paymentService);

    @Test
    void should_get_payment_from_service() {
        //given
        String id = "123";
        Payment payment = createPayment("123");
        when(payment.getId()).thenReturn(id);
        when(paymentService.get(id)).thenReturn(of(payment));

        //when
        PaymentDto paymentDto = paymentResource.getPayment(id);

        //then
        verify(paymentService).get(id);
        assertThat(paymentDto.getId()).isEqualTo(id);
        assertThat(paymentDto.getCurrency()).isEqualTo(payment.getCurrency());
        assertThat(paymentDto.getAmount()).isEqualTo(payment.getAmount());
        assertThat(paymentDto.getBankAccountNumber()).isEqualTo(payment.getBankAccountNumber());
        assertThat(paymentDto.getUserId()).isEqualTo(paymentDto.getUserId());

    }

    @Test
    void should_throw_not_found_exception_if_payment_not_exist() {
        //given
        String id = "123";
        when(paymentService.get(id)).thenReturn(empty());

        //when
        assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() -> paymentResource.getPayment(id))
                .withMessageContaining(id);

        //then
        verify(paymentService).get(id);
    }

    @Test
    void should_fetch_all_payments() {
        //given
        List<Payment> payments = singletonList(createPayment("123"));
        when(paymentService.findAll()).thenReturn(payments);

        //when
        List<PaymentDto> paymentDtos = paymentResource.getPayments();

        //then
        verify(paymentService).findAll();
        assertThat(paymentDtos).hasSize(payments.size());
        assertThat(paymentDtos.get(0).getId()).isEqualTo(payments.get(0).getId());
        assertThat(paymentDtos.get(0).getAmount()).isEqualTo(payments.get(0).getAmount());
        assertThat(paymentDtos.get(0).getUserId()).isEqualTo(payments.get(0).getUserId());
        assertThat(paymentDtos.get(0).getCurrency()).isEqualTo(payments.get(0).getCurrency());
        assertThat(paymentDtos.get(0).getBankAccountNumber()).isEqualTo(payments.get(0).getBankAccountNumber());
    }

    @Test
    void should_create_payment() {
        //give
        String id = "123";
        PaymentForm form = new PaymentForm();
        when(paymentService.addPayment(any())).thenReturn(id);

        //when
        MessageResponse messageResponse = paymentResource.create(form);


        //then
        verify(paymentService).addPayment(any(Payment.class));
        assertThat(messageResponse.getMessage()).isEqualTo(id);
        assertThat(messageResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void should_update_payment() {
        //give
        String id = "123";
        PaymentForm form = new PaymentForm();

        //when
        HttpStatus status = paymentResource.update(id, form);

        //then
        verify(paymentService).update(eq(id), any(Payment.class));
        assertThat(status).isEqualTo(HttpStatus.OK);
    }

    @Test
    void should_delete_payment() {
        //given
        String id = "123";
        //when
        HttpStatus status = paymentResource.delete(id);

        //then
        verify(paymentService).remove(id);
        assertThat(status).isEqualTo(HttpStatus.OK);

    }

    private Payment createPayment(String id) {
        Payment payment = mock(Payment.class);
        when(payment.getId()).thenReturn(id);
        when(payment.getAmount()).thenReturn(BigDecimal.ONE);
        when(payment.getCurrency()).thenReturn("PL");
        when(payment.getUserId()).thenReturn(2);
        when(payment.getBankAccountNumber()).thenReturn("7833484");

        return payment;
    }
}