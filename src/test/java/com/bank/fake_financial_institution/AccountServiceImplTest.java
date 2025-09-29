package com.bank.fake_financial_institution;

import com.bank.fake_financial_institution.dto.AccountDTO;
import com.bank.fake_financial_institution.dto.CurrencyDTO;
import com.bank.fake_financial_institution.dto.CustomerDTO;
import com.bank.fake_financial_institution.entity.Account;
import com.bank.fake_financial_institution.entity.Currency;
import com.bank.fake_financial_institution.entity.Customer;
import com.bank.fake_financial_institution.repository.AccountRepository;
import com.bank.fake_financial_institution.repository.CurrencyRepository;
import com.bank.fake_financial_institution.repository.CustomerRepository;
import com.bank.fake_financial_institution.serviceImpl.AccountServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Customer customer;
    private Currency currency;
    private Account account;
    private AccountDTO accountDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setCustomerNumber("CUSTOMER-20250928");
        customer.setName("Arisha Barron");

        currency = new Currency();
        currency.setCode("DZD");
        currency.setName("dinar algérien");
        currency.setSymbol("DA");

        account = new Account();
        account.setAccountNumber("ACC001");
        account.setBalance(BigDecimal.valueOf(1000));
        account.setCustomer(customer);
        account.setCurrency(currency);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerNumber("CUSTOMER-20250928");
        CurrencyDTO currencyDTO = new CurrencyDTO();
        currencyDTO.setCode("DZD");

        accountDTO = new AccountDTO("ACC001", "US00BANK123456789", BigDecimal.valueOf(1000), customerDTO, currencyDTO);
    }

    @Test
    void testFindAllSuccess() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Account> accountPage = new PageImpl<>(List.of(account));

        when(customerRepository.findByCustomerNumber("CUSTOMER-20250928")).thenReturn(Optional.of(customer));
        when(accountRepository.findAllByCustomer(customer, pageable)).thenReturn(accountPage);

        Page<AccountDTO> result = accountService.findAll(pageable, "CUSTOMER-20250928");

        assertEquals(1, result.getTotalElements());
        assertEquals("ACC001", result.getContent().get(0).getAccountNumber());
    }

    @Test
    void testFindAllCustomerNotFound() {
        Pageable pageable = PageRequest.of(0, 10);
        when(customerRepository.findByCustomerNumber("CUSTOMER-99999999")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                accountService.findAll(pageable, "CUSTOMER-99999999"));

        assertEquals("Customer with number CUSTOMER-99999999 not found", exception.getMessage());
    }

    @Test
    void testCreateSuccess() {
        when(customerRepository.findByCustomerNumber("CUSTOMER-20250928")).thenReturn(Optional.of(customer));
        when(currencyRepository.findByCode("DZD")).thenReturn(Optional.of(currency));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDTO result = accountService.create(accountDTO);

        assertNotNull(result);
        assertEquals("ACC001", result.getAccountNumber());
        assertEquals("DZD", result.getCurrency().getCode());
    }

    @Test
    void testCreateCustomerNotFound() {
        when(customerRepository.findByCustomerNumber("CUSTOMER-99999999")).thenReturn(Optional.empty());

        accountDTO.getCustomer().setCustomerNumber("CUSTOMER-99999999");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                accountService.create(accountDTO));

        assertEquals("Customer with number CUSTOMER-99999999 not found", exception.getMessage());
    }

    @Test
    void testCreateCurrencyNotFound() {
        when(customerRepository.findByCustomerNumber("CUSTOMER-20250928")).thenReturn(Optional.of(customer));
        when(currencyRepository.findByCode("XXX")).thenReturn(Optional.empty());

        accountDTO.getCurrency().setCode("XXX");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                accountService.create(accountDTO));

        assertEquals("Currency : XXX not found", exception.getMessage());
    }


}
