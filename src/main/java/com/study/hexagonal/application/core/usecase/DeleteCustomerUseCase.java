package com.study.hexagonal.application.core.usecase;

import com.study.hexagonal.application.ports.in.DeleteCustomerInputPort;
import com.study.hexagonal.application.ports.in.FindCustomerInputPort;
import com.study.hexagonal.application.ports.out.DeleteCustomerOutputPort;

public class DeleteCustomerUseCase implements DeleteCustomerInputPort {

    private final FindCustomerInputPort findCustomerInputPort;
    private final DeleteCustomerOutputPort deleteCustomerOutputPort;

    public DeleteCustomerUseCase(
            FindCustomerInputPort findCustomerInputPort,
            DeleteCustomerOutputPort deleteCustomerOutputPort
    ) {
        this.findCustomerInputPort = findCustomerInputPort;
        this.deleteCustomerOutputPort = deleteCustomerOutputPort;
    }

    @Override
    public void delete(String id) {
        findCustomerInputPort.find(id);
        deleteCustomerOutputPort.delete(id);
    }
}
