package com.study.hexagonal.adapters.in.controller.mapper;

import com.study.hexagonal.adapters.in.controller.request.CustomerRequest;
import com.study.hexagonal.adapters.in.controller.response.CustomerResponse;
import com.study.hexagonal.application.core.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerInputMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "isValidCpf", ignore = true)
    Customer toDomain(CustomerRequest customerRequest);

    CustomerResponse toResponse(Customer customer);
}
