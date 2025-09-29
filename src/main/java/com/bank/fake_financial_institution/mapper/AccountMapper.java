package com.bank.fake_financial_institution.mapper;

import com.bank.fake_financial_institution.dto.AccountDTO;
import com.bank.fake_financial_institution.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDTO toDto(Account account);

    Account toEntity(AccountDTO accountDTO);

}
