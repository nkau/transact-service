package com.momentum.invest.transactservice.repositories;

import com.momentum.invest.transactservice.entities.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorRepository extends JpaRepository<Investor,Long> {

    @Query(value = "select x from investor x where x.mobileNumber= :investorId or x.emailAddress= :investorId")
    Investor findInvestorByInvestorIdentifier(String investorId);
}
