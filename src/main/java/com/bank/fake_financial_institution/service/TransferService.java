package com.bank.fake_financial_institution.service;

import com.bank.fake_financial_institution.dto.TransferDTO;

public interface TransferService {

    TransferDTO create(TransferDTO accountDTO);

}
