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
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactServiceImpl implements TransactService {

    private final InvestorRepository investorRepository;

    private final ProductRepository productRepository;

    private static final Integer PERMITTED_AGE = 65;

    private static final Integer PERMITTED_WITHDRAWAL_PERCENTAGE = 90;

    public TransactServiceImpl(InvestorRepository investorRepository, ProductRepository productRepository){
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
        if(Objects.isNull(investor))
            throw new TransactServiceException(String.format("Investor not found for given id %s",request.getInvestorId()));

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
        return new WithdrawalResponse(openingBalance,closingBalance,transactionAmount,"SUCCESS");
    }

    private Product getTransactingProduct(Investor investor,String productName,String productType){
        Product product = null;
        Optional<Product> optionalProduct = investor.getProducts().stream().filter(x -> (x.getName().equalsIgnoreCase(productName) &&
                x.getType().equalsIgnoreCase(productType))).findFirst();
        if(optionalProduct.isPresent()) {
            product = optionalProduct.get();
        }
        return product;
    }

    private BigDecimal getNewBalance(Product product, BigDecimal amount, TransactionType transactionType){
        BigDecimal result;
        if(transactionType.equals(TransactionType.WITHDRAWAL))
            result = product.getBalance().subtract(amount);
        else
            result = product.getBalance().add(amount);
       return result;
    }

    void updateProductBalance(Product product, BigDecimal amount, TransactionType transactionType){
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
        BigDecimal withdrawalPercentage = (withdrawalAmount.divide(currentBalance, RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(100));
        return withdrawalPercentage.compareTo(BigDecimal.valueOf(PERMITTED_WITHDRAWAL_PERCENTAGE)) < 0 ;
    }

    private void validateWithdrawalRequest(Investor investor, WithdrawalRequest request) throws TransactServiceException {
        var product = getTransactingProduct(investor,request.getFromAccount(),request.getFromAccountType());
        if(Objects.isNull(product))
            throw new TransactServiceException(String.format("Product not found %s for Investor %s",request.getFromAccount(),request.getInvestorId()));
        if(request.getFromAccount().equalsIgnoreCase("RETIREMENT") && !isWithinAllowedAgeGroup(getInvestorAge(investor)))
            throw new TransactServiceException(String.format("Below minimum age %s permitted for withdrawals from retirement account.",PERMITTED_AGE));
        if(!isWithinAllowedTransactionAmount(product.getBalance(),request.getAmount()))
            throw new TransactServiceException(String.format("Transaction amount above allowed threshold of %s",PERMITTED_WITHDRAWAL_PERCENTAGE));
    }
}
