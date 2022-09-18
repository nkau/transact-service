package com.momentum.invest.transactservice.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "product")
@Getter @Setter
public class Product
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name = "product_name")
    private String name;

    private String type;

    @Column(name = "balance")
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "investor_id")
    private Investor investor;

    public Product(){}
}
