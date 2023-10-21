package com.example.springboottls.services;

import com.example.springboottls.IntegrationsTestConfiguration;
import com.example.springboottls.dto.customerDto.CustomerCreateEditDto;
import com.example.springboottls.dto.customerDto.CustomerReadDto;
import com.example.springboottls.exceptions.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = IntegrationsTestConfiguration.class)
@Transactional
class CustomerServiceTest {
    private static final Long CUSTOMER_ID = 1L;

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @Test
    void findAll() {
        List<CustomerReadDto> allCustomers = customerServiceImpl.findAll();
        assertThat(allCustomers).hasSize(7);
    }

    @Test
    void findCustomerById() {
        CustomerReadDto customer = customerServiceImpl.findCustomerById(CUSTOMER_ID);
        assertEquals(1, customer.getId());
        assertEquals("Ivan", customer.getName());
        assertEquals("ivan@mail.ru", customer.getEmail());
        assertEquals(18, customer.getAge());
    }

    @Test
    void createCustomer() {
        CustomerCreateEditDto customerDto = CustomerCreateEditDto.builder()
                .name("test")
                .email("test@mail.ru")
                .age(19)
                .build();
        CustomerReadDto customer = customerServiceImpl.createCustomer(customerDto);
        assertNotNull(customer);
        assertEquals("test", customer.getName());
        assertEquals("test@mail.ru", customer.getEmail());
        assertEquals(19, customer.getAge());
    }

    @Test
    void updateCustomer() {
        CustomerCreateEditDto customerDto = CustomerCreateEditDto.builder()
                .name("test")
                .email("test@mail.ru")
                .age(19)
                .build();
        CustomerReadDto customer = customerServiceImpl.updateCustomer(CUSTOMER_ID, customerDto);
        assertNotNull(customer);
        assertEquals(CUSTOMER_ID, customer.getId());
        assertEquals("test", customer.getName());
        assertEquals("test@mail.ru", customer.getEmail());
        assertEquals(19, customer.getAge());
    }

    @Test
    void deleteById() {
        CustomerReadDto customerById = customerServiceImpl.findCustomerById(CUSTOMER_ID);
        assertNotNull(customerById);
        customerServiceImpl.deleteById(CUSTOMER_ID);
        assertThrows(CustomerNotFoundException.class, () -> customerServiceImpl.findCustomerById(CUSTOMER_ID));
    }
}