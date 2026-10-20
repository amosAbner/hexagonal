package com.study.hexagonal.adapters.out.repository.mapper;

import com.study.hexagonal.adapters.out.repository.entity.CustomerEntity;
import com.study.hexagonal.application.core.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerEntity toEntity(Customer customer);
}
