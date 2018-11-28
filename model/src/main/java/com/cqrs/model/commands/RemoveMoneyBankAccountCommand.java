package com.cqrs.model.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

public class RemoveMoneyBankAccountCommand {

    @TargetAggregateIdentifier
    private String id;
    private BigDecimal value;

    public RemoveMoneyBankAccountCommand() {
    }

    public RemoveMoneyBankAccountCommand(BigDecimal value) {
        this.value = value;
    }

    public RemoveMoneyBankAccountCommand(String id, BigDecimal value) {
        this.id = id;
        this.value = value;
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
}