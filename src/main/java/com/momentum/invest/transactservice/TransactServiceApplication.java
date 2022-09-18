package com.momentum.invest.transactservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class TransactServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactServiceApplication.class, args);
	}

}
