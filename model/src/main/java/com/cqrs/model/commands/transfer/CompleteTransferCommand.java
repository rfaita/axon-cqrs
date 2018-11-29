package com.cqrs.model.commands.transfer;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CompleteTransferCommand {

    @TargetAggregateIdentifier
    private String transactionId;

    public CompleteTransferCommand() {
    }

    public CompleteTransferCommand(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

}
