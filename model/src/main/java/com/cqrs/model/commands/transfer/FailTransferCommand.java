package com.cqrs.model.commands.transfer;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class FailTransferCommand {

    @TargetAggregateIdentifier
    private String transactionId;

    public FailTransferCommand() {
    }

    public FailTransferCommand(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

}
