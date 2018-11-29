package com.cqrs.model.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

public class WithdrawnMoneyBankAccountCommand {

    @TargetAggregateIdentifier
    private String id;
    private String transactionId;
    private BigDecimal value;

    public WithdrawnMoneyBankAccountCommand() {
    }

    public WithdrawnMoneyBankAccountCommand(String id, String transactionId, BigDecimal value) {
        this.id = id;
        this.transactionId = transactionId;
        this.value = value;
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

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}