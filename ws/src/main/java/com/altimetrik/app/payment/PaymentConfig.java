package com.altimetrik.app.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class PaymentConfig {

    @Bean
    @Profile("csv")
    PaymentRepository CSVpaymentRepository(@Value("${payment.csv-repository.path}") String filePath) {
        return new CSVpaymentRepository(createIfNotExists(filePath));
    }


    @Bean
    @Profile("inMemory")
    PaymentRepository inMemoryPaymentRepository() {
        return new InMemoryPaymentRepository();
    }

    @Bean
    PaymentService paymentService(PaymentRepository paymentRepository) {
        return new PaymentServiceImpl(paymentRepository);
    }

    private Path createIfNotExists(String filePath) {
        Path path;
        try {
            path = Paths.get(filePath);
            path = Paths.get("/home/kru02/workspace/payments_exercise/core/src/main/resources/payments.csv");
            path.toFile().createNewFile();
            if (!path.toFile().exists()) {
                path.toFile().createNewFile();
//                Files.createFile(filePath);
            }
        } catch (RuntimeException | IOException e) {
//            LOGGER.error("Cannot create file for path = {}");
            throw new IllegalStateException(String.format("Cannot create file for path '%s'", filePath));
        }
        return path;
    }

}
