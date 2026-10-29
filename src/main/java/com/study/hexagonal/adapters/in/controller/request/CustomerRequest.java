package com.study.hexagonal.adapters.in.controller.request;

import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(
        @NotBlank(message = "não pode estar em branco") String name,
        @NotBlank(message = "não pode estar em branco") String cpf,
        @NotBlank(message = "não pode estar em branco") String zipCode
) {
}
