package com.bank.fake_financial_institution.serviceImpl;

import com.bank.fake_financial_institution.dto.TransferDTO;
import com.bank.fake_financial_institution.entity.Account;
import com.bank.fake_financial_institution.entity.AccountTransaction;
import com.bank.fake_financial_institution.entity.TransactionDirection;
import com.bank.fake_financial_institution.repository.AccountRepository;
import com.bank.fake_financial_institution.repository.AccountTransactionRepository;
import com.bank.fake_financial_institution.repository.CustomerRepository;
import com.bank.fake_financial_institution.service.TransferService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferServiceImpl implements TransferService {

    private final AccountRepository accountRepository;
    private final AccountTransactionRepository accountTransactionRepository;

    public TransferServiceImpl(AccountRepository accountRepository,
                               AccountTransactionRepository accountTransactionRepository) {
        this.accountRepository = accountRepository;
        this.accountTransactionRepository = accountTransactionRepository;
    }

    @Override
    @Transactional
    public TransferDTO create(TransferDTO transferDTO) {

        Account debitorAccount = accountRepository.findByAccountNumber(transferDTO.getCreditAccountNumber()).
                orElseThrow(() -> new EntityNotFoundException("debitorAccount not found: " + transferDTO.getCreditAccountNumber()));

        Account creditorAccount = accountRepository.findByAccountNumber(transferDTO.getDebitAccountNumber()).
                orElseThrow(() -> new EntityNotFoundException("creditorAccount not found: " + transferDTO.getDebitAccountNumber()));

        if (debitorAccount.getBalance().compareTo(transferDTO.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient funds in debitor account: " + debitorAccount.getAccountNumber());
        }

        AccountTransaction accountTransactionForDebitor = createAccountTransaction(debitorAccount, transferDTO.getAmount(), TransactionDirection.OUTGOING);
        accountTransactionRepository.save(accountTransactionForDebitor);

        AccountTransaction accountTransactionForCreditor = createAccountTransaction(creditorAccount, transferDTO.getAmount(), TransactionDirection.INCOMING);
        accountTransactionRepository.save(accountTransactionForCreditor);

        debitorAccount.setBalance(debitorAccount.getBalance().subtract(transferDTO.getAmount()));
        accountRepository.save(debitorAccount);

        creditorAccount.setBalance(creditorAccount.getBalance().add(transferDTO.getAmount()));
        accountRepository.save(creditorAccount);

        return transferDTO;
    }

    private AccountTransaction createAccountTransaction(Account account, BigDecimal amount, TransactionDirection direction) {
        AccountTransaction transaction = new AccountTransaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setDirection(direction);
        return transaction;
    }
}
