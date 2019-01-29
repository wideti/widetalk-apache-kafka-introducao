package com.github.widelabs.transactionvalidation.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TransactionService {

    private KafkaTemplate<Integer, String> kafkaTemplate;
    private ObjectMapper objectMapper;

    @Autowired
    public TransactionService(KafkaTemplate<Integer, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public CheckedTransaction check(Transaction transaction) {
        boolean isValid = AccountDB.check(transaction);

        if (isValid) {
            return CheckedTransaction.buildFrom(transaction, CheckedTransaction.Status.APPROVED);
        }

        return CheckedTransaction.buildFrom(transaction, CheckedTransaction.Status.REPROVED);
    }

    public void publishChecked(CheckedTransaction transaction) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(transaction);
        ProducerRecord<Integer, String> record = new ProducerRecord<>("checked_transaction", transaction.getId(), message);
        kafkaTemplate.send(record);
    }
}

class AccountDB {
    private static Map<String, Double> accounts;

    static {
        accounts = new HashMap<>();
        accounts.put("leo", 100.00);
        accounts.put("raj", 5000.00);
        accounts.put("evandrao", 1.99);
        accounts.put("jaozinho", 1.00);
        accounts.put("romani", 9.90);
        accounts.put("eder", 0.0);
    }

    public static boolean check(Transaction transaction) {
        Double value = transaction.getValue();
        String user = transaction.getUser();
        Double accountValue = AccountDB.accounts.get(user);

        if (accountValue == null) {
            return false;
        }

        return accountValue >= value;

    }
}