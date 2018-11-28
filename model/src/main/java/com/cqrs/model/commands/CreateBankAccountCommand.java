package com.cqrs.model.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateBankAccountCommand {

    @TargetAggregateIdentifier
    private String id;
    private String name;

    public CreateBankAccountCommand() {
    }

    public CreateBankAccountCommand(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}