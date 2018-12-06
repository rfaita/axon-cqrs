package com.cqrs.model.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WithdrawnMoneyBankAccountCommand {

    @TargetAggregateIdentifier
    private String id;
    private String transactionId;
    private BigDecimal value;
}