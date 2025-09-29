package com.bank.fake_financial_institution;

import com.bank.fake_financial_institution.dto.CustomerDTO;
import com.bank.fake_financial_institution.entity.Customer;
import com.bank.fake_financial_institution.repository.CustomerRepository;
import com.bank.fake_financial_institution.serviceImpl.CustomerServiceImpl;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private CustomerDTO customerDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setCustomerNumber("CUSTOMER-20250928");
        customer.setName("Arisha Barron");

        customerDTO = new CustomerDTO();
        customerDTO.setCustomerNumber("CUSTOMER-20250928");
        customerDTO.setName("Branden Gibson");
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> customerPage = new PageImpl<>(List.of(customer));

        when(customerRepository.findAll(pageable)).thenReturn(customerPage);

        Page<CustomerDTO> result = customerService.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("CUSTOMER-20250928", result.getContent().get(0).getCustomerNumber());
    }

    @Test
    void testDetailFound() {
        when(customerRepository.findByCustomerNumber("CUSTOMER-20250928")).thenReturn(Optional.of(customer));

        CustomerDTO result = customerService.detail("CUSTOMER-20250928");

        assertNotNull(result);
        assertEquals("CUSTOMER-20250928", result.getCustomerNumber());
    }

    @Test
    void testDetailNotFound() {
        when(customerRepository.findByCustomerNumber("CUSTOMER-99999999")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                customerService.detail("CUSTOMER-99999999"));

        assertEquals("Customer with number CUSTOMER-99999999 not found", exception.getMessage());
    }

    @Test
    void testCreate() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO result = customerService.create(customerDTO);

        assertNotNull(result);
        assertEquals("CUSTOMER-20250928", result.getCustomerNumber());
    }

    @Test
    void testDelete() {
        when(customerRepository.findByCustomerNumber("CUSTOMER-20250928")).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).delete(customer);

        assertDoesNotThrow(() -> customerService.delete("CUSTOMER-20250928"));
        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    void testDeleteNotFound() {
        when(customerRepository.findByCustomerNumber("CUSTOMER-99999999")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                customerService.delete("CUSTOMER-99999999"));

        assertEquals("Customer with number CUSTOMER-99999999 not found", exception.getMessage());
    }
}
