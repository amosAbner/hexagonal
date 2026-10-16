package com.study.hexagonal.adapters.out.client.mapper;

import com.study.hexagonal.adapters.out.client.response.AddressResponse;
import com.study.hexagonal.application.core.domain.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressResponseMapper {

    Address toDomain(AddressResponse addressResponse);
}
