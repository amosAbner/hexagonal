package com.study.hexagonal.adapters.in.controller;

import com.study.hexagonal.adapters.in.controller.request.CustomerRequest;
import com.study.hexagonal.adapters.mapper.CustomerRequestMapper;
import com.study.hexagonal.application.core.domain.Customer;
import com.study.hexagonal.application.ports.in.InsertCustomerInputPort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private InsertCustomerInputPort inputPort;

    @Autowired
    CustomerRequestMapper requestMapper;

    @PostMapping()
    public ResponseEntity<Customer> insertCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        var customer = requestMapper.toDomain(customerRequest);
        inputPort.insert(customer, customerRequest.zipCode());

        return ResponseEntity.ok().build();
    }
}
