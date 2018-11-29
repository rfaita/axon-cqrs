package com.cqrs.model.events.transfer;

public class TransferCompletedEvent {

    private String transactionId;

    public TransferCompletedEvent() {
    }

    public TransferCompletedEvent(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

}
