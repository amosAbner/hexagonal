package com.study.hexagonal.adapters.in.controller.response;

public record CustomerResponse(
        String id,
        String name,
        String cpf,
        Boolean isValidCpf,
        Address address
) {
    public record Address(
            String street,
            String city,
            String state
    ){}
}
