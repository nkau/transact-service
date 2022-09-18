package com.momentum.invest.transactservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class WithdrawalResponse {

    private BigDecimal openingBalance;
    private BigDecimal closingBalance;
    private BigDecimal transactionalAmount;
    private String result;
}
