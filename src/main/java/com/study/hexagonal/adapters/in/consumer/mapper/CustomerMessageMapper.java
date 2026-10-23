package com.study.hexagonal.adapters.in.consumer.mapper;

import com.study.hexagonal.adapters.in.consumer.message.CustomerMessage;
import com.study.hexagonal.application.core.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMessageMapper {

    @Mapping(target = "address", ignore = true)
    Customer toDomain(CustomerMessage customerMessage);
}
