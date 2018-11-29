package com.cqrs.projection.mongo.server.query.type;

import java.io.Serializable;

public class BankAccountQuery implements Serializable {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
