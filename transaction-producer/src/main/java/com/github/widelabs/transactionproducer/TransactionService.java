package com.github.widelabs.transactionproducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper;

    @Autowired
    public TransactionService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(Transaction transaction) throws JsonProcessingException {
        String transactionJson = objectMapper.writeValueAsString(transaction);
        ProducerRecord<String, String> record = new ProducerRecord<>("new_transaction", transaction.getUser(), transactionJson);
        kafkaTemplate.send(record);
    }
}
