package com.study.hexagonal.config.bean;

import com.study.hexagonal.adapters.out.DeleteCustomerAdapter;
import com.study.hexagonal.application.core.usecase.DeleteCustomerUseCase;
import com.study.hexagonal.application.core.usecase.FindCustomerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeleteCustomerConfig {

    @Bean
    public DeleteCustomerUseCase deleteCustomerUseCase(
            FindCustomerUseCase findCustomerUseCase,
            DeleteCustomerAdapter deleteCustomerAdapter
    ){
        return new DeleteCustomerUseCase(findCustomerUseCase, deleteCustomerAdapter);
    }
}
