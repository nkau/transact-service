package com.momentum.invest.transactservice.controllers;

import com.momentum.invest.transactservice.dtos.InvestorDto;
import com.momentum.invest.transactservice.dtos.WithdrawalRequest;
import com.momentum.invest.transactservice.exceptions.TransactServiceException;
import com.momentum.invest.transactservice.services.WithdrawalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WithdrawalController {

    private final WithdrawalService withdrawalService;

    public WithdrawalController(WithdrawalService withdrawalService){
        this.withdrawalService =  withdrawalService;
    }

    @GetMapping(path = "/investor/getAll")
    public ResponseEntity<List<InvestorDto>> getAllInvestors(){

        var investors = withdrawalService.getAllInvestors();

        return ResponseEntity.ok(investors);
    }

    @GetMapping(path="/investor/{investorId}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<InvestorDto> getInvestorByIdentifier(@PathVariable String investorId){
        var investor = withdrawalService.getInvestorByInvestorIdentifier(investorId);

        return ResponseEntity.ok(investor);
    }

    @PostMapping(path = "/withdraw")
    @PreAuthorize("hasAnyAuthority('investor:write')")
    public void doWithdrawal(WithdrawalRequest request) throws TransactServiceException {
        withdrawalService.doWithdrawal(request);
    }
}
