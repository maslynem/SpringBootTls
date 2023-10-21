package com.example.springboottls.services;

import com.example.springboottls.dto.customerDto.CustomerCreateEditDto;
import com.example.springboottls.dto.customerDto.CustomerReadDto;

import java.util.List;

public interface CustomerService {
    CustomerReadDto findCustomerById(Long id);

    List<CustomerReadDto> findAll();

    CustomerReadDto createCustomer(CustomerCreateEditDto customerDto);

    void deleteById(Long id);

    CustomerReadDto updateCustomer(Long id, CustomerCreateEditDto customerDto);
}
