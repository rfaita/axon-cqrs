package com.cqrs.model.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

public class AddMoneyBankAccountCommand {

    @TargetAggregateIdentifier
    private String id;
    private String transactionId;
    private BigDecimal value;

    public AddMoneyBankAccountCommand() {
    }

    public AddMoneyBankAccountCommand(String id, String transactionId, BigDecimal value) {
        this.id = id;
        this.value = value;
        this.transactionId = transactionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}