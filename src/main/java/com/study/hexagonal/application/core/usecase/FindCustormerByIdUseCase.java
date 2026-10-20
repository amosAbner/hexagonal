package com.study.hexagonal.application.core.usecase;

import com.study.hexagonal.application.core.domain.Customer;
import com.study.hexagonal.application.ports.in.FindCustomerInputPort;
import com.study.hexagonal.application.ports.out.FindCustomerByIdOutputPort;

public class FindCustormerByIdUseCase implements FindCustomerInputPort {

    private final FindCustomerByIdOutputPort findCustomerByIdOutputPort;

    public FindCustormerByIdUseCase(FindCustomerByIdOutputPort outputPort) {
        this.findCustomerByIdOutputPort = outputPort;
    }

    @Override
    public Customer find(String id) {
        return findCustomerByIdOutputPort.find(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}
