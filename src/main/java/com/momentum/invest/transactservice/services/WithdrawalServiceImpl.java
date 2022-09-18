package com.momentum.invest.transactservice.services;

import com.momentum.invest.transactservice.dtos.InvestorDto;
import com.momentum.invest.transactservice.dtos.TransactionType;
import com.momentum.invest.transactservice.dtos.WithdrawalRequest;
import com.momentum.invest.transactservice.dtos.WithdrawalResponse;
import com.momentum.invest.transactservice.entities.Investor;
import com.momentum.invest.transactservice.entities.Product;
import com.momentum.invest.transactservice.exceptions.TransactServiceException;
import com.momentum.invest.transactservice.repositories.InvestorRepository;
import com.momentum.invest.transactservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class WithdrawalServiceImpl implements WithdrawalService{

    private final InvestorRepository investorRepository;

    private final ProductRepository productRepository;

    private static final Integer PERMITTED_AGE = 60;

    private static final Integer PERMITTED_WITHDRAWAL_PERCENTAGE = 90;

    public WithdrawalServiceImpl(InvestorRepository investorRepository, ProductRepository productRepository){
        this.investorRepository = investorRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<InvestorDto> getAllInvestors() {
        var investors = investorRepository.findAll();
        return investors.stream().map(InvestorDto::new).collect(Collectors.toList());
    }

    @Override
    public InvestorDto getInvestorByInvestorIdentifier(String investorId) {
        var investor = investorRepository.findInvestorByInvestorIdentifier(investorId);
        return new InvestorDto(investor);
    }

    @Override
    public WithdrawalResponse doWithdrawal(WithdrawalRequest request) throws TransactServiceException {

        var investor = investorRepository.findInvestorByInvestorIdentifier(request.getInvestorId());

        WithdrawalResponse withDrawalResponse;
        validateWithdrawalRequest(investor,request);

        var transactingProduct = getTransactingProduct(investor,request.getFromAccount(),request.getFromAccountType());

        updateProductBalance(transactingProduct, request.getAmount(),TransactionType.WITHDRAWAL);

        withDrawalResponse = buildWithdrawalResponse(transactingProduct.getBalance(),
                getNewBalance(transactingProduct,request.getAmount(),TransactionType.WITHDRAWAL),
                request.getAmount());

        return withDrawalResponse;
    }

    private WithdrawalResponse buildWithdrawalResponse(BigDecimal openingBalance,BigDecimal closingBalance,
                                                       BigDecimal transactionAmount){
        return new WithdrawalResponse(openingBalance,closingBalance,transactionAmount,"");
    }

    private Product getTransactingProduct(Investor investor,String productName,String productType){
        return investor.getProducts().stream().filter(product -> (product.getName().equalsIgnoreCase(productName) &&
                product.getType().equalsIgnoreCase(productType))).findFirst().get();
    }

    private BigDecimal getNewBalance(Product product, BigDecimal amount, TransactionType transactionType){
        BigDecimal result;
        if(transactionType.equals(TransactionType.WITHDRAWAL))
            result = product.getBalance().subtract(amount);
        else
            result = product.getBalance().add(amount);
       return result;
    }

    private void updateProductBalance(Product product, BigDecimal amount,TransactionType transactionType){
        productRepository.updateProductBalance(product.getId(),getNewBalance(product,amount,transactionType));
    }

    private int getInvestorAge(Investor investor){
        LocalDate today = LocalDate.now();
        Period period = Period.between(investor.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),today);
        return period.getYears();
    }

    private boolean isWithinAllowedAgeGroup(Integer age){
        return age > PERMITTED_AGE;
    }

    private boolean isWithinAllowedTransactionAmount(BigDecimal currentBalance,BigDecimal withdrawalAmount){
        return (withdrawalAmount.divide(currentBalance).multiply(BigDecimal.valueOf(100)).compareTo(BigDecimal.valueOf(PERMITTED_WITHDRAWAL_PERCENTAGE))) < 0 ;
    }

    private void validateWithdrawalRequest(Investor investor, WithdrawalRequest request) throws TransactServiceException {
        var product = getTransactingProduct(investor,request.getFromAccount(),request.getFromAccountType());
        if(Objects.isNull(product))
            throw new TransactServiceException(String.format("Product not found %s for Investor %s",request.getFromAccount(),request.getInvestorId()));
            //throw exception since user cannot transact against product.
            //if the product type is retirement, then investor age should be greater than 60
        if(request.getAccountType().equalsIgnoreCase("RETIREMENT") && !isWithinAllowedAgeGroup(getInvestorAge(investor)))
            throw new TransactServiceException(String.format("Below minimum age %s permitted for withdrawals from retirement account.",PERMITTED_AGE));
            //throw exception if not within specified age group, move thresh hold age to application properties.
            //check that the investor has specified product
            //check that the withdrawal amount is not more than 90% of balance
        if(!isWithinAllowedTransactionAmount(product.getBalance(),request.getAmount()))
            throw new TransactServiceException(String.format("Transaction amount above allowed threshold of %s %",PERMITTED_WITHDRAWAL_PERCENTAGE));
    }
}
