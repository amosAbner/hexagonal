package com.study.hexagonal.adapters.in.controller;

import com.study.hexagonal.adapters.in.controller.request.CustomerRequest;
import com.study.hexagonal.adapters.in.controller.mapper.CustomerInputMapper;
import com.study.hexagonal.adapters.in.controller.response.CustomerResponse;
import com.study.hexagonal.application.core.domain.Customer;
import com.study.hexagonal.application.ports.in.FindCustomerInputPort;
import com.study.hexagonal.application.ports.in.InsertCustomerInputPort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private InsertCustomerInputPort insertCustomerInputPort;

    @Autowired
    private FindCustomerInputPort findCustomerInputPort;

    @Autowired
    private CustomerInputMapper customerMapper;

    @PostMapping()
    public ResponseEntity<Customer> insertCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        var customer = customerMapper.toDomain(customerRequest);
        insertCustomerInputPort.insert(customer, customerRequest.zipCode());

        return ResponseEntity.created(
                URI.create("/api/v1/customers/" + customer.getId())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable final String id) {
        var customer = findCustomerInputPort.find(id);
        var customerResponse = customerMapper.toResponse(customer);

        return ResponseEntity.ok().body(customerResponse);
    }
}
