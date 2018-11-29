package com.cqrs.model.events.transfer;

public class TransferFailedEvent {

    private String transactionId;

    public TransferFailedEvent() {
    }

    public TransferFailedEvent(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

}
