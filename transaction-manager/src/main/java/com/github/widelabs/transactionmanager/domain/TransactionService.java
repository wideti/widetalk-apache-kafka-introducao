package com.github.widelabs.transactionmanager.domain;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    public void save(Integer id, Transaction transaction) {
        TransactionDatabase.add(id, transaction);
    }

    public Transaction getOne(Integer id) throws TransactionNotFound {
        return TransactionDatabase.get(id).orElseThrow(TransactionNotFound::new);
    }

    public List<Transaction> getAll() {
        return TransactionDatabase.getAll();
    }
}
