package com.study.hexagonal.adapters.out;

import com.study.hexagonal.adapters.out.repository.CustomerRepository;
import com.study.hexagonal.application.ports.out.DeleteCustomerOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteCustomerAdapter implements DeleteCustomerOutputPort {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void delete(String id) {
        customerRepository.deleteById(id);
    }
}
