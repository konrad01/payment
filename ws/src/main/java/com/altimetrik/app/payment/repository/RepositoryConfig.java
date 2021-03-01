package com.altimetrik.app.payment.repository;

import com.altimetrik.app.payment.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@Slf4j
public class RepositoryConfig {
    @Bean
    @Profile("csv")
    PaymentRepository CSVPaymentRepository(@Value("${payment.csv-repository.path}") String filePath) {
        return new CSVPaymentRepository(createIfNotExists(filePath));
    }

    @Bean
    @Profile("inMemory")
    PaymentRepository inMemoryPaymentRepository() {
        return new InMemoryPaymentRepository();
    }

    private Path createIfNotExists(String filePath) {
        Path path;
        try {
            path = Paths.get(filePath);
            if (!path.toFile().exists()) {
                log.info("creating new repository file for path = {}", filePath);
                path.toFile().createNewFile();
            }
        } catch (Exception e) {
            log.error("Exception during create file for path = {}", filePath, e);
            throw new IllegalStateException(String.format("Cannot create file for path '%s'", filePath));
        }
        return path;
    }
}
