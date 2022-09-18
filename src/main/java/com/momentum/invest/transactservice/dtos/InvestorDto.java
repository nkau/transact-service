package com.momentum.invest.transactservice.dtos;

import com.momentum.invest.transactservice.entities.Investor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter @Setter
public class InvestorDto {

    private String name;
    private String surname;
    private String email;
    private String contactNumber;
    private Set<ProductDto> products;

    public InvestorDto(Investor investor){
        this.name = investor.getName();
        this.surname = investor.getSurname();
        this.email = investor.getEmailAddress();
        this.contactNumber = investor.getMobileNumber();
        this.products = investor.getProducts().stream().map(ProductDto::new).collect(Collectors.toSet());
    }
}
