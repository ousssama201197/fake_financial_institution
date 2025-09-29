package com.bank.fake_financial_institution.serviceImpl;

import com.bank.fake_financial_institution.repository.CustomerRepository;
import com.bank.fake_financial_institution.dto.CustomerDTO;
import com.bank.fake_financial_institution.entity.Customer;
import com.bank.fake_financial_institution.mapper.CustomerMapper;
import com.bank.fake_financial_institution.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Page<CustomerDTO> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable).map(
                customer -> {
                    customerMapper.toDto(customer);
                    return customerMapper.toDto(customer);
                }
        );
    }

    @Override
    public CustomerDTO detail(String customerNumber) {
        return customerMapper.toDto(customerRepository.findByCustomerNumber(customerNumber).orElseThrow(
                () -> new EntityNotFoundException("Customer with number " + customerNumber + " not found")));
    }

    @Override
    public CustomerDTO create(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        return customerMapper.toDto(customerRepository.save(customer));
    }

}
