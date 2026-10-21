package com.study.hexagonal.adapters.in.controller;

import com.study.hexagonal.adapters.in.controller.request.CustomerRequest;
import com.study.hexagonal.adapters.in.controller.mapper.CustomerInputMapper;
import com.study.hexagonal.adapters.in.controller.response.CustomerResponse;
import com.study.hexagonal.application.ports.in.FindCustomerInputPort;
import com.study.hexagonal.application.ports.in.InsertCustomerInputPort;
import com.study.hexagonal.application.ports.in.UpdateCustomerInputPort;
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
    UpdateCustomerInputPort updateCustomerInputPort;

    @Autowired
    private CustomerInputMapper customerMapper;

    @PostMapping()
    public ResponseEntity<Void> insertCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
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

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable final String id,
                                               @Valid @RequestBody CustomerRequest customerRequest) {
        var customer = customerMapper.toDomain(customerRequest);
        customer.setId(id);
        updateCustomerInputPort.update(customer, customerRequest.zipCode());

        return ResponseEntity.noContent().build();
    }
}
