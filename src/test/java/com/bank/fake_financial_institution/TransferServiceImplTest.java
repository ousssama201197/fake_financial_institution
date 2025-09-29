package com.bank.fake_financial_institution;

import com.bank.fake_financial_institution.dto.TransferDTO;
import com.bank.fake_financial_institution.entity.Account;
import com.bank.fake_financial_institution.entity.AccountTransaction;
import com.bank.fake_financial_institution.repository.AccountRepository;
import com.bank.fake_financial_institution.repository.AccountTransactionRepository;
import com.bank.fake_financial_institution.serviceImpl.TransferServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TransferServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountTransactionRepository accountTransactionRepository;

    @InjectMocks
    private TransferServiceImpl transferService;

    private Account debitorAccount;
    private Account creditorAccount;
    private TransferDTO transferDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        debitorAccount = new Account();
        debitorAccount.setAccountNumber("ACC001");
        debitorAccount.setBalance(BigDecimal.valueOf(1000));

        creditorAccount = new Account();
        creditorAccount.setAccountNumber("ACC002");
        creditorAccount.setBalance(BigDecimal.valueOf(500));

        transferDTO = new TransferDTO();
        transferDTO.setAmount(BigDecimal.valueOf(200));
        transferDTO.setDebitAccountNumber("ACC002");
        transferDTO.setCreditAccountNumber("ACC001");
        transferDTO.setPurpose("Payment");
    }

    @Test
    void testCreateTransferSuccess() {
        when(accountRepository.findByAccountNumber("ACC001")).thenReturn(Optional.of(debitorAccount));
        when(accountRepository.findByAccountNumber("ACC002")).thenReturn(Optional.of(creditorAccount));
        when(accountTransactionRepository.save(any(AccountTransaction.class))).thenReturn(new AccountTransaction());
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TransferDTO result = transferService.create(transferDTO);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(800), debitorAccount.getBalance());
        assertEquals(BigDecimal.valueOf(700), creditorAccount.getBalance());
    }

    @Test
    void testDebitorAccountNotFound() {
        when(accountRepository.findByAccountNumber("ACC001")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                transferService.create(transferDTO));

        assertEquals("debitorAccount not found: ACC001", exception.getMessage());
    }

    @Test
    void testCreditorAccountNotFound() {
        when(accountRepository.findByAccountNumber("ACC001")).thenReturn(Optional.of(debitorAccount));
        when(accountRepository.findByAccountNumber("ACC002")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                transferService.create(transferDTO));

        assertEquals("creditorAccount not found: ACC002", exception.getMessage());
    }

    @Test
    void testInsufficientFunds() {
        debitorAccount.setBalance(BigDecimal.valueOf(100)); // less than transfer amount

        when(accountRepository.findByAccountNumber("ACC001")).thenReturn(Optional.of(debitorAccount));
        when(accountRepository.findByAccountNumber("ACC002")).thenReturn(Optional.of(creditorAccount));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                transferService.create(transferDTO));

        assertEquals("Insufficient funds in debitor account: ACC001", exception.getMessage());
    }
}


