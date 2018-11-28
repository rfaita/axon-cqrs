package com.cqrs.model.dto;

import java.math.BigDecimal;

public class BankAccountDTO {

    private String name;
    private BigDecimal balance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BankAccountDTO() {
    }

    public BankAccountDTO(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
    }
}