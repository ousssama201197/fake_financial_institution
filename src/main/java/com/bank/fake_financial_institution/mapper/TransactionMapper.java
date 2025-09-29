package com.bank.fake_financial_institution.mapper;

import com.bank.fake_financial_institution.dto.TransactionDTO;
import com.bank.fake_financial_institution.entity.AccountTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionDTO toDto(AccountTransaction accountTransaction);

    AccountTransaction toEntity(TransactionDTO transactionDTO);

}
