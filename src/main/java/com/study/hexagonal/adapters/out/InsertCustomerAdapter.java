package com.study.hexagonal.adapters.out;

import com.study.hexagonal.adapters.out.repository.mapper.CustomerMapper;
import com.study.hexagonal.adapters.out.repository.CustomerRepository;
import com.study.hexagonal.application.core.domain.Customer;
import com.study.hexagonal.application.ports.out.InsertCustomerOutputPort;
import org.springframework.beans.factory.annotation.Autowired;

public class InsertCustomerAdapter implements InsertCustomerOutputPort {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public void insert(Customer customer) {
        var customerEntity = customerMapper.toEntity(customer);
        customerRepository.save(customerEntity);
    }
}
