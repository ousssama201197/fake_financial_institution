package com.bank.fake_financial_institution.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrencyDTO {

    private String code;

    private String name;

    private String symbol;

}
