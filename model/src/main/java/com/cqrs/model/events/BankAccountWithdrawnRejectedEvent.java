package com.cqrs.model.events;

import java.math.BigDecimal;

public class BankAccountWithdrawnRejectedEvent {

    private String id;
    private String transactionId;

    public BankAccountWithdrawnRejectedEvent() {
    }

    public BankAccountWithdrawnRejectedEvent(String id, String transactionId) {
        this.id = id;
        this.transactionId = transactionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

}
