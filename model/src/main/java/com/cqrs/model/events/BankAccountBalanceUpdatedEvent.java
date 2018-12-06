package com.cqrs.model.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountBalanceUpdatedEvent {

    private String id;
    private String transactionId;
    private BigDecimal balance;

}
