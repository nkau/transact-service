package com.momentum.invest.transactservice.controllers;

import com.momentum.invest.transactservice.dtos.InvestorDto;
import com.momentum.invest.transactservice.dtos.WithdrawalRequest;
import com.momentum.invest.transactservice.dtos.WithdrawalResponse;
import com.momentum.invest.transactservice.exceptions.TransactServiceException;
import com.momentum.invest.transactservice.services.TransactService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactController {

    private final TransactService transactService;

    public TransactController(TransactService transactService){
        this.transactService =  transactService;
    }

    @GetMapping(path = "/investor/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InvestorDto>> getAllInvestors(){

        var investors = transactService.getAllInvestors();

        return ResponseEntity.ok(investors);
    }

    @GetMapping(path="/investor/{investorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InvestorDto> getInvestorByIdentifier(@PathVariable String investorId){
        var investor = transactService.getInvestorByInvestorIdentifier(investorId);

        return ResponseEntity.ok(investor);
    }

    @PostMapping(path = "/withdraw")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('INVESTOR')")
    public WithdrawalResponse doWithdrawal(@RequestBody WithdrawalRequest request) throws TransactServiceException {
        return transactService.doWithdrawal(request);
    }
}
