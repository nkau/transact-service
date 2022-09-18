package com.momentum.invest.transactservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class WithdrawalRequest {

    private String investorId;
    private String fromAccount;
    private String fromAccountType;
    private String toAccount;
    private String accountType;
    private BigDecimal amount;

}
