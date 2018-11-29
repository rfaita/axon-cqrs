package com.cqrs.projection.mongo.server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

@Document
public class BankAccount implements Serializable {

    @Id
    private String id;
    private String name;
    private BigDecimal balance;

    public String getId() {
        return id;
    }

    public BankAccount setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public BankAccount setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BankAccount setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public static class Builder {

        public static BankAccount create() {
            return new BankAccount();
        }

    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
