package com.altimetrik.app.payment;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PaymentServiceImplTest {
    private final PaymentRepository repository = Mockito.mock(PaymentRepository.class);
    private final PaymentService service = new PaymentServiceImpl(repository);

    @Test
    void should_call_repository_on_get_operation() {
        //given
        String id = "id";

        //when
        service.get(id);

        //then
        verify(repository).findById(id);
    }

    @Test
    void should_call_repository_on_find_all_operation() {
        //when
        service.findAll();

        //then
        verify(repository).findAll();
    }

    @Test
    void should_call_repository_on_add_payment_all_operation() {
        //given
        Payment payment = Mockito.mock(Payment.class);
        String id = "123";
        when(payment.getId()).thenReturn(id);

        //when
        String paymentId = service.addPayment(payment);

        //then
        verify(repository).save(payment);
        assertThat(paymentId).isEqualTo(id);
    }

    @Test
    void should_call_repository_on_remove_operation() {
        //given
        String id = "id";

        //when
        service.remove(id);

        //then
        verify(repository).delete(id);
    }

    @Test
    void should_call_repository_on_update_operation() {
        //given
        String id = "id";
        Payment payment = Mockito.mock(Payment.class);
        when(payment.getId()).thenReturn(id);

        //when
        service.update(id, payment);

        //then
        verify(repository).update(id, payment);
    }
}