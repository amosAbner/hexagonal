package com.study.hexagonal.application.ports.out;

import com.study.hexagonal.application.core.domain.Customer;

public interface UpdateCustomerOutputPort {

    void update(Customer customer, String zipCode);
}
