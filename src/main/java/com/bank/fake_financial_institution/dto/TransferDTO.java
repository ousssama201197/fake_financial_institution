package com.bank.fake_financial_institution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransferDTO {

    private BigDecimal amount;

    private String purpose;

    private String debitAccountNumber;

    private String creditAccountNumber;


}
