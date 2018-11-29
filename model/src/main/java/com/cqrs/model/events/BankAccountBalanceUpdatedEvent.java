package com.cqrs.model.events;

import java.math.BigDecimal;

public class BankAccountBalanceUpdatedEvent {

    private String id;
    private String transactionId;
    private BigDecimal balance;


    public BankAccountBalanceUpdatedEvent() {
    }

    public BankAccountBalanceUpdatedEvent(String id, String transactionId, BigDecimal balance) {
        this.id = id;
        this.transactionId = transactionId;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
