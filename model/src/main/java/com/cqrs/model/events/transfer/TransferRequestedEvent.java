package com.cqrs.model.events.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferRequestedEvent {

    private String transactionId;
    private String sourceId;
    private String destinationId;
    private BigDecimal amount;
}
