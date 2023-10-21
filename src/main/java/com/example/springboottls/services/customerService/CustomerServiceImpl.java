package com.example.springboottls.services.customerService;

import com.example.springboottls.dto.customerDto.CustomerCreateEditDto;
import com.example.springboottls.dto.customerDto.CustomerReadDto;
import com.example.springboottls.entities.Company;
import com.example.springboottls.entities.Customer;
import com.example.springboottls.exceptions.CompanyNotFoundException;
import com.example.springboottls.exceptions.CustomerNotFoundException;
import com.example.springboottls.mapper.CustomerCreateEditMapper;
import com.example.springboottls.mapper.CustomerReadMapper;
import com.example.springboottls.repository.CompanyRepository;
import com.example.springboottls.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    private final CustomerReadMapper customerReadMapper;
    private final CustomerCreateEditMapper customerCreateEditMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CompanyRepository companyRepository, CustomerReadMapper customerReadMapper, CustomerCreateEditMapper customerCreateEditMapper) {
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
        this.customerReadMapper = customerReadMapper;
        this.customerCreateEditMapper = customerCreateEditMapper;
    }

    @Override
    public CustomerReadDto findCustomerById(Long id) {
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
    public List<CustomerReadDto> findPaginated(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return customerRepository.findAllBy(pageable).stream().map(customerReadMapper::map).toList();
    }

    @Override
    public CustomerReadDto createCustomer(CustomerCreateEditDto customerDto) {
        Company company = companyRepository.findByName(customerDto.getCompanyName())
                .orElseThrow(() -> new CompanyNotFoundException(String.format("Company [%s] does not exist", customerDto.getCompanyName())));
        Customer customer = customerCreateEditMapper.map(customerDto);
        customer.setCompany(company);
        customerRepository.save(customer);
        return customerReadMapper.map(customer);
    }

    @Override
    public void deleteById(final Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerReadDto updateCustomer(Long id, CustomerCreateEditDto customerDto) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id '%d' not found", id)));
        Company company = companyRepository
                .findByName(customerDto.getCompanyName())
                .orElseThrow(() -> new CompanyNotFoundException(String.format("Company [%s] does not exist", customerDto.getCompanyName())));
        customer.setCompany(company);
        customerCreateEditMapper.map(customerDto, customer);
        customerRepository.save(customer);
        return customerReadMapper.map(customer);
    }
}
