package com.momentum.invest.transactservice.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "investor")
@Getter @Setter
public class Investor {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private String surname;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    private String address;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @OneToMany(mappedBy ="investor")
    private Set<Product> products;

    public Investor(){}
}
