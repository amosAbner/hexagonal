package com.study.hexagonal.application.ports.out;

import com.study.hexagonal.application.core.domain.Address;

public interface FindAddressByZipCodeOutputPort {

    Address find(String zipCode);
}
