package com.departmentstore.api.infrastructure.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI departmentStoreOpenApi() {

        return new OpenAPI().info(
                        new Info()
                            .title("Department Store API")
                            .description("API para gerenciamento de Pessoas, Funcionários, Clientes e Responsáveis.")
                                .version("v1.0.0")
                                .contact(new Contact()
                                            .name("Department Store Team")
                                            .email("admin@departmentstore.com")
                                )
                                .license(new License().name("Apache 2.0"))
                )
                .externalDocs(new ExternalDocumentation().description("Documentação do Projeto"));
    }

}
