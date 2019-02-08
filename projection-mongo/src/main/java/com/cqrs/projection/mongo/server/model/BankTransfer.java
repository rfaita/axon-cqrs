package com.cqrs.projection.mongo.server.model;

import com.cqrs.projection.mongo.server.enums.BankTransferStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class BankTransfer implements Serializable {

    @Id
    private String transactionId;
    private String sourceId;
    private String destinationId;
    private BigDecimal amount;
    private BankTransferStatus status;
}
