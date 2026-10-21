package com.study.hexagonal.adapters.out;

import com.study.hexagonal.adapters.out.repository.CustomerRepository;
import com.study.hexagonal.adapters.out.repository.mapper.CustomerOutputMapper;
import com.study.hexagonal.application.core.domain.Customer;
import com.study.hexagonal.application.ports.out.UpdateCustomerOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateCustomerAdapter implements UpdateCustomerOutputPort {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerOutputMapper customerOutputMapper;

    @Override
    public void update(Customer customer) {
        var customerEntity = customerOutputMapper.toEntity(customer);
        customerRepository.save(customerEntity);
    }
}
