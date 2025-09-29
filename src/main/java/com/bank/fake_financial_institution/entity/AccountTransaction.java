package com.bank.fake_financial_institution.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "account_transaction")
@Getter
@Setter
@NoArgsConstructor
public class AccountTransaction extends AbstractEntity {


    private BigDecimal amount;

    private String purpose;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    private TransactionDirection direction;

}
