package com.altimetrik.app.payment;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.RequiredArgsConstructor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class CSVpaymentRepository implements PaymentRepository {

    private final Path repositoryPath;

    @Override
    public List<Payment> findAll() {
        return fetchAll().stream()
                .map(PaymentAdapter::new)
                .collect(toList());
    }

    @Override
    public void delete(int id) {
        remove(id);
    }

    @Override
    public void save(Payment payment) {
        persist(payment);
    }

    private static class PaymentAdapter implements Payment {

        private final PaymentModel model;

        private PaymentAdapter(PaymentModel model) {
            this.model = model;
        }

        @Override
        public Integer getId() {
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

    private void persist(Payment payment) {
        List<PaymentModel> payments = fetchAll();
        PaymentModel model = toModel(payment);
        OptionalInt index = checkIfExists(model.getId(), payments);
        if (index.isPresent()) {
            payments.set(index.getAsInt(), model);
        } else {
            payments.add(model);
        }
        write(payments);
    }

    private void remove(int id) {
        List<PaymentModel> payments = fetchAll();
        checkIfExists(id, payments).ifPresent(index -> {
            payments.remove(index);
            write(payments);
        });

    }

    private OptionalInt checkIfExists(Integer id, List<PaymentModel> payments) {
        return IntStream.range(0, payments.size())
                .filter(i -> id.equals(payments.get(i).getId()))
                .findFirst();
    }

    private <I> void write(List<I> payments) {
        try (Writer writer = new FileWriter(repositoryPath.toFile())) {
            new StatefulBeanToCsvBuilder<I>(writer)
                    .build()
                    .write(payments);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<PaymentModel> fetchAll() {
        try (Reader reader = new FileReader(repositoryPath.toFile())) {
            return new CsvToBeanBuilder<PaymentModel>(reader)
                    .withType(PaymentModel.class)
                    .build()
                    .parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emptyList();
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


}
