package com.study.hexagonal.config;

import com.study.hexagonal.adapters.out.FindCustomerAdapter;
import com.study.hexagonal.application.core.usecase.FindCustomerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindCustomerConfig {

    @Bean
    public FindCustomerUseCase findCustomerUseCase(
            FindCustomerAdapter findCustomerAdapter
    ) {
        return new FindCustomerUseCase(findCustomerAdapter);
    }
}
