package com.study.hexagonal.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customerApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hexagonal Customer API")
                        .description("API para cadastro, consulta, atualização e exclusão de clientes.")
                        .version("v1"));
    }
}
