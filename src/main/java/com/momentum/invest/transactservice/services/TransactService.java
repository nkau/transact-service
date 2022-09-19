package com.momentum.invest.transactservice.services;

import com.momentum.invest.transactservice.dtos.InvestorDto;
import com.momentum.invest.transactservice.dtos.WithdrawalRequest;
import com.momentum.invest.transactservice.dtos.WithdrawalResponse;
import com.momentum.invest.transactservice.exceptions.TransactServiceException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TransactService {

    List<InvestorDto> getAllInvestors();

    InvestorDto getInvestorByInvestorIdentifier(String investorId);

    WithdrawalResponse doWithdrawal(WithdrawalRequest request) throws TransactServiceException;
}
