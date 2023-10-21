package com.example.springboottls.mapper;

import com.example.springboottls.dto.customerDto.CustomerCreateEditDto;
import com.example.springboottls.entities.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerCreateEditMapper {
    private final ModelMapper mapper;

    @Autowired
    public CustomerCreateEditMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Customer map(CustomerCreateEditDto customerCreateEditDto) {
        return mapper.map(customerCreateEditDto, Customer.class);
    }

    public void map(CustomerCreateEditDto customerCreateEditDto, Customer customer) {
        mapper.map(customerCreateEditDto, customer);
    }
}
