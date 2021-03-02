package com.altimetrik.app.payment;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class CSVPaymentRepository implements PaymentRepository {

    private final Path repositoryPath;

    @Override
    public Optional<Payment> findById(String id) {
        return find(id).map(PaymentAdapter::new);
    }

    @Override
    public List<Payment> findAll() {
        return fetchAll().stream()
                .map(PaymentAdapter::new)
                .collect(toList());
    }

    @Override
    public void delete(String id) {
        remove(id);
    }

    @Override
    public String save(Payment payment) {
        PaymentModel model = toModel(payment);
        String id = UUID.randomUUID().toString();
        model.setId(id);
        persist(model);

        return id;
    }

    @Override
    public void update(String id, Payment payment) {
        PaymentModel model = toModel(payment);
        model.setId(id);
        persist(model);
    }

    private void persist(PaymentModel model) {
        List<PaymentModel> payments = fetchAll();
        OptionalInt index = checkIfExists(model.getId(), payments);
        if (index.isPresent()) {
            payments.set(index.getAsInt(), model);
        } else {
            payments.add(model);
        }
        write(payments);
    }

    private void remove(String id) {
        List<PaymentModel> payments = fetchAll();
        checkIfExists(id, payments).ifPresent(index -> {
            payments.remove(index);
            write(payments);
        });

    }

    private Optional<PaymentModel> find(String id) {
        if (isNull(id)) {
            return empty();
        }
        List<PaymentModel> payments = fetchAll();
        OptionalInt index = checkIfExists(id, fetchAll());
        if (index.isPresent()) {
            return of(payments.get(index.getAsInt()));
        }
        return empty();
    }

    private OptionalInt checkIfExists(String id, List<PaymentModel> payments) {
        if (isNull(id)) {
            return OptionalInt.empty();
        }
        return IntStream.range(0, payments.size())
                .filter(i -> id.equals(payments.get(i).getId()))
                .findFirst();
    }

    private <I> void write(List<I> inputs) {
        try (Writer writer = new FileWriter(repositoryPath.toFile())) {
            new StatefulBeanToCsvBuilder<I>(writer)
                    .build()
                    .write(inputs);
        } catch (Exception e) {
            log.error("Exception during persisting data to csv repository", e);
            throw new RepositoryOperationException(e.getMessage(), e.getCause());
        }
    }

    private List<PaymentModel> fetchAll() {
        try (Reader reader = new FileReader(repositoryPath.toFile())) {
            return new CsvToBeanBuilder<PaymentModel>(reader)
                    .withType(PaymentModel.class)
                    .build()
                    .parse();
        } catch (Exception e) {
            log.error("Exception during fetching data from csv repository", e);
            throw new RepositoryOperationException(e.getMessage(), e.getCause());
        }
    }


    private PaymentModel toModel(Payment payment) {
        PaymentModel model = new PaymentModel();
        model.setId(payment.getId());
        model.setAmount(payment.getAmount());
        model.setCurrency(payment.getCurrency());
        model.setUserId(payment.getUserId());
        model.setBankAccountNumber(payment.getBankAccountNumber());

        return model;
    }

    private static class PaymentAdapter implements Payment {

        private final PaymentModel model;

        private PaymentAdapter(PaymentModel model) {
            this.model = model;
        }

        @Override
        public String getId() {
            return model.getId();
        }

        @Override
        public BigDecimal getAmount() {
            return model.getAmount();
        }

        @Override
        public String getCurrency() {
            return model.getCurrency();
        }

        @Override
        public Integer getUserId() {
            return model.getUserId();
        }

        @Override
        public String getBankAccountNumber() {
            return model.getBankAccountNumber();
        }
    }

}
