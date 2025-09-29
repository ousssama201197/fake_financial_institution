package com.bank.fake_financial_institution.mapper;

import com.bank.fake_financial_institution.dto.CustomerDTO;
import com.bank.fake_financial_institution.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO toDto(Customer customer);

    Customer toEntity(CustomerDTO customerDTO);

}
