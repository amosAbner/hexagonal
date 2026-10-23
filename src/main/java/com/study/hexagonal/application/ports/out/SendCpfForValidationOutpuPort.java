package com.study.hexagonal.application.ports.out;

public interface SendCpfForValidationOutpuPort {

    void send(String cpf);
}
