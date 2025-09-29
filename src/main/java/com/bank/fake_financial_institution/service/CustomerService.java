package com.bank.fake_financial_institution.service;

import com.bank.fake_financial_institution.dto.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    Page<CustomerDTO> findAll(Pageable pageable);

    CustomerDTO detail(String customerNumber);

    CustomerDTO create(CustomerDTO customerDTO);

}
