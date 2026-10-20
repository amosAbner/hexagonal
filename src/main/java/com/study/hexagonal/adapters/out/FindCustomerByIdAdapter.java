package com.study.hexagonal.adapters.out;

import com.study.hexagonal.adapters.out.repository.CustomerRepository;
import com.study.hexagonal.adapters.out.repository.mapper.CustomerOutputMapper;
import com.study.hexagonal.application.core.domain.Customer;
import com.study.hexagonal.application.ports.out.FindCustomerByIdOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindCustomerByIdAdapter implements FindCustomerByIdOutputPort {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerOutputMapper customerOutputMapper;

    @Override
    public Optional<Customer> find(String id) {
        var customerEntity = customerRepository.findById(id);
        return customerEntity.map(customerOutputMapper::toDomain);
    }
}
