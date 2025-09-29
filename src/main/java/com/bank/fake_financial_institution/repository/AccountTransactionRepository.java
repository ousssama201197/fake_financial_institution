package com.bank.fake_financial_institution.repository;

import com.bank.fake_financial_institution.entity.Account;
import com.bank.fake_financial_institution.entity.AccountTransaction;
import com.bank.fake_financial_institution.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {


}
