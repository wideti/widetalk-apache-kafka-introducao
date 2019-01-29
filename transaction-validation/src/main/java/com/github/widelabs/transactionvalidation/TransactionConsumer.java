package com.github.widelabs.transactionvalidation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.widelabs.transactionvalidation.domain.TransactionService;
import com.github.widelabs.transactionvalidation.domain.CheckedTransaction;
import com.github.widelabs.transactionvalidation.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TransactionConsumer {

    private TransactionService transactionService;
    private ObjectMapper objectMapper;

    @Autowired
    public TransactionConsumer(TransactionService checkTransactionService, ObjectMapper objectMapper) {
        this.transactionService = checkTransactionService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = {"new_transaction"}, groupId = "validation_service_group")
    public void listen(@Header(value = KafkaHeaders.RECEIVED_MESSAGE_KEY) String key, @Payload String message, Acknowledgment acknowledgment) throws IOException {
        Transaction transaction = objectMapper.readValue(message, Transaction.class);

        CheckedTransaction check = transactionService.check(transaction);
        transactionService.publishChecked(check);
        acknowledgment.acknowledge();

    }
}
