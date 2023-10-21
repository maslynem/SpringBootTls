package com.example.springboottls.mapper;

import com.example.springboottls.dto.customerDto.CustomerReadDto;
import com.example.springboottls.entities.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerReadMapper {
    private final ModelMapper mapper;

    @Autowired
    public CustomerReadMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public CustomerReadDto map(Customer customer) {
        return mapper.map(customer, CustomerReadDto.class);
    }

}
