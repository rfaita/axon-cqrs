package com.cqrs.model.commands.transfer;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

public class RequestTransferCommand {

    @TargetAggregateIdentifier
    private String transactionId;
    private String sourceId;
    private String destinationId;
    private BigDecimal amount;

    public RequestTransferCommand() {
    }

    public RequestTransferCommand(String transactionId, String sourceId, String destinationId, BigDecimal amount) {
        this.transactionId = transactionId;
        this.sourceId = sourceId;
        this.destinationId = destinationId;
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
