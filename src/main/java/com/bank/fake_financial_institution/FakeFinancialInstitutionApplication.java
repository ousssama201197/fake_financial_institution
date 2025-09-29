package com.bank.fake_financial_institution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.bank.fake_financial_institution")
public class FakeFinancialInstitutionApplication {

    public static void main(String[] args) {
        SpringApplication.run(FakeFinancialInstitutionApplication.class, args);
    }

}
