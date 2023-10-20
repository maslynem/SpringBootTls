package com.example.springboottls.controllers;

import com.example.springboottls.dto.customerDto.CustomerCreateEditDto;
import com.example.springboottls.dto.customerDto.CustomerReadDto;
import com.example.springboottls.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerReadDto> findAllCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public CustomerReadDto findCustomerById(@PathVariable final Long id) {
        return customerService.findCustomerById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerReadDto create(@RequestBody CustomerCreateEditDto customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CustomerReadDto update(@PathVariable("id") Long id, @RequestBody CustomerCreateEditDto user) {
        return customerService.updateCustomer(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        customerService.deleteById(id);
    }

}
