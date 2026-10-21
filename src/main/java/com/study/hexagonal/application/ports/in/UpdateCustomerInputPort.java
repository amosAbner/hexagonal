package com.study.hexagonal.application.ports.in;

import com.study.hexagonal.application.core.domain.Customer;

public interface UpdateCustomerInputPort {

    void update(Customer customer, String zipCode);
}
