package com.study.hexagonal.adapters.out.client;

import com.study.hexagonal.adapters.out.client.response.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "FindAddressByZipCodeClient", url = "${client.address.url}")
public interface FindAddressByZipCodeClient {

    @GetMapping("/{zipCode}/json/")
    AddressResponse findAddressByZipCode(@PathVariable("zipCode") String zipCode);
}
