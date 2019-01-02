package com.cqrs.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankTransferDTO {

    private String sourceId;
    private String destinationId;
    private BigDecimal amount;

}