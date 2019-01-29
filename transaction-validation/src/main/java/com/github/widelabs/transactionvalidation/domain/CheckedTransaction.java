package com.github.widelabs.transactionvalidation.domain;

public class CheckedTransaction extends Transaction {

    private Status status;

    private CheckedTransaction() {
    }

    public static CheckedTransaction buildFrom(Transaction transaction, Status status) {
        CheckedTransaction ct = new CheckedTransaction();
        ct.setId(transaction.getId());
        ct.setUser(transaction.getUser());
        ct.setValue(transaction.getValue());
        ct.setStatus(status);
        return ct;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        APPROVED, REPROVED;
    }
}
