package com.study.hexagonal.adapters.in.consumer;

import com.study.hexagonal.adapters.in.consumer.mapper.CustomerMessageMapper;
import com.study.hexagonal.adapters.in.consumer.message.CustomerMessage;
import com.study.hexagonal.application.ports.out.UpdateCustomerOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ReceivedValidatedCpfConsumer {

    @Autowired
    private UpdateCustomerOutputPort updateCustomerOutputPort;

    @Autowired
    private CustomerMessageMapper customerMessageMapper;

    @KafkaListener(topics = "tp-cpf-validated", groupId = "kafka-config")
    public void consume(CustomerMessage customerMessage) {
        var customer = customerMessageMapper.toDomain(customerMessage);
        updateCustomerOutputPort.update(customer, customerMessage.zipCode());
    }
}
