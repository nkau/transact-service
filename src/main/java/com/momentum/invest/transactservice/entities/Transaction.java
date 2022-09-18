package com.momentum.invest.transactservice.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "Transaction")
@Getter @Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

/*
    @ManyToOne
    @JoinColumn(name = "investor_id")
    Investor investor;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
*/

    Date transactionDate;

    BigDecimal initialBalance;

    BigDecimal closingBalance;

    BigDecimal transactionAmount;
}
