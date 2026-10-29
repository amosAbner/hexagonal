package com.study.hexagonal.application.core.usecase;

import com.study.hexagonal.application.core.domain.Customer;
import com.study.hexagonal.application.ports.in.FindCustomerInputPort;
import com.study.hexagonal.application.ports.in.UpdateCustomerInputPort;
import com.study.hexagonal.application.ports.out.FindAddressByZipCodeOutputPort;
import com.study.hexagonal.application.ports.out.SendCpfForValidationOutpuPort;
import com.study.hexagonal.application.ports.out.UpdateCustomerOutputPort;

import java.util.Objects;

public class UpdateCustomerUseCase implements UpdateCustomerInputPort {

    private final FindCustomerInputPort findCustomerInputPort;
    private final FindAddressByZipCodeOutputPort findAddressByZipCodeOutputPort;
    private final UpdateCustomerOutputPort updateCustomerOutputPort;
    private final SendCpfForValidationOutpuPort sendCpfForValidationOutpuPort;

    public UpdateCustomerUseCase(
            FindCustomerInputPort findCustomerInputPort,
            FindAddressByZipCodeOutputPort findAddressByZipCodeOutputPort,
            UpdateCustomerOutputPort updateCustomerOutputPort,
            SendCpfForValidationOutpuPort sendCpfForValidationOutpuPort
    ) {
        this.findCustomerInputPort = findCustomerInputPort;
        this.findAddressByZipCodeOutputPort = findAddressByZipCodeOutputPort;
        this.updateCustomerOutputPort = updateCustomerOutputPort;
        this.sendCpfForValidationOutpuPort = sendCpfForValidationOutpuPort;
    }

    @Override
    public void update(Customer customer, String zipCode) {
        var customerFound = findCustomerInputPort.find(customer.getId());
        var address = findAddressByZipCodeOutputPort.find(zipCode);

        var cpfChanged = !Objects.equals(customerFound.getCpf(), customer.getCpf());
        customerFound.setName(customer.getName());
        customerFound.setCpf(customer.getCpf());
        customerFound.setAddress(address);
        if (cpfChanged) {
            customerFound.setIsValidCpf(false);
        }

        updateCustomerOutputPort.update(customerFound, zipCode);
        if (cpfChanged) {
            sendCpfForValidationOutpuPort.send(customerFound.getCpf());
        }
    }
}
