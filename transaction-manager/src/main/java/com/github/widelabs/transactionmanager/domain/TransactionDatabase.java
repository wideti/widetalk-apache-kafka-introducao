package com.github.widelabs.transactionmanager.domain;

import java.util.*;

public class TransactionDatabase {
    private static final Map<Integer, Transaction> store;

    static {
        store = new HashMap<>();
    }

    public static void add(Integer id, Transaction transaction) {
        store.put(id, transaction);
    }

    public static Optional<Transaction> get(Integer id) {
        return Optional.ofNullable(store.get(id));
    }

    public static List<Transaction> getAll() {
        final List<Transaction> transactions = new ArrayList<>();
        store.forEach((key, transaction) -> transactions.add(transaction));
        return transactions;
    }
}
