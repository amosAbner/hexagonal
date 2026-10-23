package com.study.hexagonal.config;

import com.study.hexagonal.adapters.out.FindAddressByZipCodeAdapter;
import com.study.hexagonal.adapters.out.UpdateCustomerAdapter;
import com.study.hexagonal.application.core.usecase.FindCustomerUseCase;
import com.study.hexagonal.application.core.usecase.UpdateCustomerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateCustomerConfig {

    @Bean
    public UpdateCustomerUseCase updateCustomerUseCase(
            FindCustomerUseCase findCustomerUseCase,
            FindAddressByZipCodeAdapter findAddressByZipCodeOutputPort,
            UpdateCustomerAdapter updateCustomerOutputPort
    ){
        return new UpdateCustomerUseCase(
                findCustomerUseCase,
                findAddressByZipCodeOutputPort,
                updateCustomerOutputPort);
    }
}
