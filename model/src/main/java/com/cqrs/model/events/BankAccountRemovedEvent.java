package com.cqrs.model.events;

public class BankAccountRemovedEvent {

    private String id;

    public BankAccountRemovedEvent() {
    }

    public BankAccountRemovedEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
