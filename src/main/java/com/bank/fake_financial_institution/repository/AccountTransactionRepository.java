package com.bank.fake_financial_institution.repository;

import com.bank.fake_financial_institution.entity.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {


}
