package com.cqrs.model.events;

import java.math.BigDecimal;

public class BankAccountBalanceUpdatedEvent {

    private String id;
    private BigDecimal balance;


    public BankAccountBalanceUpdatedEvent() {
    }

    public BankAccountBalanceUpdatedEvent(String id, BigDecimal balance) {
        this.id = id;
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
}
