package com.study.hexagonal.application.ports.in;

import com.study.hexagonal.application.core.domain.Customer;

public interface FindCustomerInputPort {

    Customer find(String id);
}
