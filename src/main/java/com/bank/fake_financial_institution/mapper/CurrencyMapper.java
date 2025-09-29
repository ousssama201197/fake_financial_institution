package com.bank.fake_financial_institution.mapper;

import com.bank.fake_financial_institution.dto.CurrencyDTO;
import com.bank.fake_financial_institution.entity.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

    CurrencyDTO toDto(Currency currency);

    Currency toEntity(CurrencyDTO currencyDTO);

}
