package com.bank.fake_financial_institution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountDTO {

    private String accountNumber;

    private String rib;

    private BigDecimal balance;

    private CustomerDTO customer;

    private CurrencyDTO currency;

}
