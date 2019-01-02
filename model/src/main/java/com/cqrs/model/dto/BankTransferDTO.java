package com.cqrs.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class BankAccountDTO {

    private String name;


}