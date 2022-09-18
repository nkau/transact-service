package com.momentum.invest.transactservice.dtos;

import com.momentum.invest.transactservice.entities.Product;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto {
    private String name;
    private String type;
    private BigDecimal balance;

    public ProductDto(Product product){
        this.name = product.getName();
        this.type = product.getType();
        this.balance = product.getBalance();
    }
}
