package com.example.springboottls.mapper;

import com.example.springboottls.dto.customerDto.CustomerCreateEditDto;
import com.example.springboottls.entities.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerCreateEditMapper {
    private final ModelMapper mapper;

    public CustomerCreateEditMapper() {
        this.mapper = new ModelMapper();
    }

    public Customer map(final CustomerCreateEditDto customerCreateEditDto) {
        return mapper.map(customerCreateEditDto, Customer.class);
    }
}
