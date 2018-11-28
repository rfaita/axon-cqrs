package com.cqrs.model.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class RemoveBankAccountCommand {

    @TargetAggregateIdentifier
    private String id;

    public RemoveBankAccountCommand() {
    }

    public RemoveBankAccountCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}