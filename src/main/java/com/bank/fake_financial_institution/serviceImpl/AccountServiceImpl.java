package com.bank.fake_financial_institution.serviceImpl;

import com.bank.fake_financial_institution.dto.AccountDTO;
import com.bank.fake_financial_institution.entity.Account;
import com.bank.fake_financial_institution.entity.Currency;
import com.bank.fake_financial_institution.entity.Customer;
import com.bank.fake_financial_institution.mapper.AccountMapper;
import com.bank.fake_financial_institution.repository.AccountRepository;
import com.bank.fake_financial_institution.repository.CurrencyRepository;
import com.bank.fake_financial_institution.repository.CustomerRepository;
import com.bank.fake_financial_institution.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final CurrencyRepository currencyRepository;
    private final AccountMapper accountMapper = AccountMapper.INSTANCE;


    public AccountServiceImpl(AccountRepository accountRepository,
                              CustomerRepository customerRepository,
                              CurrencyRepository currencyRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Page<AccountDTO> findAll(Pageable pageable, String customerNumber) {
        Customer customer = customerRepository.findByCustomerNumber(customerNumber).orElseThrow(
                () -> new EntityNotFoundException("Customer with number " + customerNumber + " not found"));
        return accountRepository.findAllByCustomer(customer, pageable).map(accountMapper::toDto);
    }

    @Override
    public AccountDTO create(AccountDTO accountDTO) {
        Customer customer = customerRepository.findByCustomerNumber(accountDTO.getCustomer().getCustomerNumber()).orElseThrow(
                () -> new EntityNotFoundException("Customer with number " + accountDTO.getCustomer().getCustomerNumber() + " not found"));
        Currency currency = currencyRepository.findByCode(accountDTO.getCurrency().getCode()).orElseThrow(
                () -> new EntityNotFoundException("Currency : " + accountDTO.getCurrency().getCode() + " not found"));
        Account account = accountMapper.toEntity(accountDTO);
        account.setCustomer(customer);
        account.setCurrency(currency);
        account = accountRepository.save(account);
        return accountMapper.toDto(account);
    }
}
