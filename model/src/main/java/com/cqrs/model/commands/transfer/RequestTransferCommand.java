package com.cqrs.model.commands.transfer;

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
public class RequestTransferCommand {

    @TargetAggregateIdentifier
    private String transactionId;
    private String sourceId;
    private String destinationId;
    private BigDecimal amount;

}
