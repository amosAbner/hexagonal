package com.study.hexagonal.adapters.in.controller.mapper;

import com.study.hexagonal.adapters.in.controller.request.CustomerRequest;
import com.study.hexagonal.adapters.out.repository.mapper.CustomerOutputMapperImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class CustomerInputMapperTest {

    private final CustomerInputMapper inputMapper = new CustomerInputMapperImpl();
    private final CustomerOutputMapperImpl outputMapper = new CustomerOutputMapperImpl();

    @Test
    void shouldPersistAndReturnCpfValidationAsFalseForNewCustomer() {
        var customer = inputMapper.toDomain(new CustomerRequest("Maria da Silva", "12345678909", "01001000"));

        var customerEntity = outputMapper.toEntity(customer);
        var response = inputMapper.toResponse(customer);

        assertFalse(customer.getIsValidCpf());
        assertFalse(customerEntity.getIsValidCpf());
        assertFalse(response.isValidCpf());
    }
}
