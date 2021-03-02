package com.altimetrik.app.payment;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CSVPaymentRepositoryIntegrationTest {
    private static Path FILE_PATH;

    @Configuration
    static class TestConfig {
        @Bean
        @Primary
        public PaymentRepository testBeanDefinition() throws IOException {
            FILE_PATH = Files.createTempFile("repo", ".csv");
            return new CSVPaymentRepository(FILE_PATH);
        }
    }

    @Autowired
    private PaymentRepository paymentRepository;


    @Test
    void test_crud_on_repository() {
        //given
        String newBankAccount = "88888";

        //when
        String id = paymentRepository.save(new PaymentInstance(BigDecimal.TEN, "7777", "PL"));
        List<Payment> payments = paymentRepository.findAll();

        //then
        assertThat(payments).hasSize(1);

        //and when
        paymentRepository.update(id, new PaymentInstance(BigDecimal.TEN, newBankAccount, "PL"));
        Optional<Payment> payment = paymentRepository.findById(id);

        //then
        assertThat(payment.get().getBankAccountNumber()).isEqualTo(newBankAccount);

        //and when
        paymentRepository.delete(id);

        //then
        payments = paymentRepository.findAll();
        assertThat(payments).hasSize(0);
    }

    @RequiredArgsConstructor
    private static class PaymentInstance implements Payment {
        private final BigDecimal amount;
        private final String bankAccount;
        private final String currency;

        @Override
        public String getId() {
            return null;
        }

        @Override
        public BigDecimal getAmount() {
            return amount;
        }

        @Override
        public String getCurrency() {
            return currency;
        }

        @Override
        public Integer getUserId() {
            return 44;
        }

        @Override
        public String getBankAccountNumber() {
            return bankAccount;
        }
    }

    @AfterAll
    static void tearDown() {
        File tempFile = FILE_PATH.toFile();
        if (tempFile.exists()) {
            assertThat(tempFile.delete()).isTrue();
        }
    }

}