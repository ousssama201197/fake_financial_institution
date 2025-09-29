package com.bank.fake_financial_institution.dto;

import com.bank.fake_financial_institution.entity.Account;
import com.bank.fake_financial_institution.entity.TransactionDirection;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDTO {


    private BigDecimal amount;

    private String purpose;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    private TransactionDirection direction;

}
