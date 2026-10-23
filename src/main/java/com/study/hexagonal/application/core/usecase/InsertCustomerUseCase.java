package com.study.hexagonal.application.core.usecase;

import com.study.hexagonal.application.core.domain.Customer;
import com.study.hexagonal.application.ports.in.InsertCustomerInputPort;
import com.study.hexagonal.application.ports.out.FindAddressByZipCodeOutputPort;
import com.study.hexagonal.application.ports.out.InsertCustomerOutputPort;
import com.study.hexagonal.application.ports.out.SendCpfForValidationOutpuPort;

public class InsertCustomerUseCase implements InsertCustomerInputPort {

    private final FindAddressByZipCodeOutputPort findAddressByZipCodeOutputPort;
    private final InsertCustomerOutputPort insertCustomerOutputPort;
    private final SendCpfForValidationOutpuPort sendCpfForValidationOutpuPort;

    public InsertCustomerUseCase(FindAddressByZipCodeOutputPort findAddressByZipCodeOutputPort,
                                 InsertCustomerOutputPort insertCustomerOutputPort,
                                 SendCpfForValidationOutpuPort sendCpfForValidationOutpuPort) {
        this.findAddressByZipCodeOutputPort = findAddressByZipCodeOutputPort;
        this.insertCustomerOutputPort = insertCustomerOutputPort;
        this.sendCpfForValidationOutpuPort = sendCpfForValidationOutpuPort;
    }

    @Override
    public void insert(Customer customer, String zipCode) {
        var address = findAddressByZipCodeOutputPort.find(zipCode);
        customer.setAddress(address);

        insertCustomerOutputPort.insert(customer);
        sendCpfForValidationOutpuPort.send(customer.getCpf());
    }
}
