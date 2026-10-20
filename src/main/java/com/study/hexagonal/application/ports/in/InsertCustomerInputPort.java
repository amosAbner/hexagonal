package com.study.hexagonal.application.ports.in;

import com.study.hexagonal.application.core.domain.Customer;

public interface InsertCustomerInputPort {

    void insert(Customer customer, String zipCode);
}
