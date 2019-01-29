package com.github.widelabs.transactionmanager.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    private ObjectMapper objectMapper;
    private TransactionService transactionService;

    @Autowired
    public TransactionConsumer(ObjectMapper objectMapper, TransactionService transactionService) {
        this.objectMapper = objectMapper;
        this.transactionService = transactionService;
    }

    @KafkaListener(topics = {"checked_transaction"}, groupId = "manager_transaction_group")
    public void listen(
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Integer key,
            @Payload String message,
            Acknowledgment ack
    ) throws IOException {

        Transaction transaction = objectMapper.readValue(message, Transaction.class);
        transactionService.save(key, transaction);
        ack.acknowledge();
    }
}
