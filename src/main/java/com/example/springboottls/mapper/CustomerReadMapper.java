package com.example.springboottls.mapper;

import com.example.springboottls.dto.customerDto.CustomerReadDto;
import com.example.springboottls.entities.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerReadMapper {
    private final ModelMapper mapper;

    public CustomerReadMapper() {
        this.mapper = new ModelMapper();
    }

    public CustomerReadDto map(final Customer customer) {
        return mapper.map(customer, CustomerReadDto.class);
    }

}
