package com.bank.fake_financial_institution.service;

import com.bank.fake_financial_institution.dto.AccountDTO;
import com.bank.fake_financial_institution.dto.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {

    Page<AccountDTO> findAll(Pageable pageable, String CustomerNumber);

    AccountDTO create(AccountDTO accountDTO);

}
