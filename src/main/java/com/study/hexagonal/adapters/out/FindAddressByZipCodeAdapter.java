package com.study.hexagonal.adapters.out;

import com.study.hexagonal.adapters.out.client.FindAddressByZipCodeClient;
import com.study.hexagonal.adapters.in.controller.mapper.AddressMapper;
import com.study.hexagonal.application.core.domain.Address;
import com.study.hexagonal.application.ports.out.FindAddressByZipCodeOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class FindAddressByZipCodeAdapter implements FindAddressByZipCodeOutputPort {

    @Autowired
    private FindAddressByZipCodeClient client;

    @Autowired
    AddressMapper mapper;

    @Override
    public Address find(String zipCode) {
        if (zipCode == null || !zipCode.matches("[0-9]{5}-?[0-9]{3}")) {
            throw new IllegalArgumentException("O CEP deve conter oito dígitos, com hífen opcional");
        }

        var response = client.findAddressByZipCode(zipCode.replace("-", ""));
        if (response == null) {
            throw new IllegalStateException("O ViaCEP retornou uma resposta vazia");
        }
        if (Boolean.TRUE.equals(response.error())) {
            throw new NoSuchElementException("Endereço não encontrado para o CEP: " + zipCode);
        }

        return mapper.toDomain(response);
    }
}
