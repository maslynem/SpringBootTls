package com.example.springboottls.services;

import com.example.springboottls.dto.customerDto.CustomerCreateEditDto;
import com.example.springboottls.dto.customerDto.CustomerReadDto;
import com.example.springboottls.entities.Customer;
import com.example.springboottls.exceptions.CustomerNotFoundException;
import com.example.springboottls.mapper.CustomerCreateEditMapper;
import com.example.springboottls.mapper.CustomerReadMapper;
import com.example.springboottls.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerReadMapper customerReadMapper;
    private final CustomerCreateEditMapper customerCreateEditMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerReadMapper customerReadMapper, CustomerCreateEditMapper customerCreateEditMapper) {
        this.customerRepository = customerRepository;
        this.customerReadMapper = customerReadMapper;
        this.customerCreateEditMapper = customerCreateEditMapper;
    }

    @Override
    public CustomerReadDto findCustomerById(final Long id) {
        return customerRepository.findById(id)
                .map(customerReadMapper::map)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id '%d' not found", id)));
    }

    @Override
    public List<CustomerReadDto> findAll() {
        return customerRepository.findAll().stream()
                .map(customerReadMapper::map)
                .toList();
    }

    @Override
    public CustomerReadDto createCustomer(final CustomerCreateEditDto customerDto) {
        return Optional.of(customerDto)
                .map(customerCreateEditMapper::map)
                .map(customerRepository::save)
                .map(customerReadMapper::map)
                .orElseThrow();
    }

    @Override
    public void deleteById(final Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerReadDto updateCustomer(final Long id, final CustomerCreateEditDto customerDto) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id '%d' not found", id)));
        customerCreateEditMapper.map(customerDto,customer);
        customerRepository.save(customer);
        return customerReadMapper.map(customer);
    }
}
