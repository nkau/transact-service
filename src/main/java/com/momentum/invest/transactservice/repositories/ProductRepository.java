package com.momentum.invest.transactservice.repositories;

import com.momentum.invest.transactservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Modifying
    @Transactional
    @Query("update product p set p.balance = :amount where p.id = :productId")
    void updateProductBalance(Integer productId, BigDecimal amount);
}
