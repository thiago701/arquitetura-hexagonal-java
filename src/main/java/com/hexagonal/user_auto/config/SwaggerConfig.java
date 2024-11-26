package com.hexagonal.user_auto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Restful para Sistema de Usuários de Carros")
                        .version("1.0")
                        .description("Esta é a documentação da API para o sistema de gerenciamento de usuários e seus carros.")
                        .contact(new Contact()
                                .name("Thiago Gonçalo Gomes")
                                .email("thiago701@gmail.com")
                                .url("http://instagram.com/thiago2gg")));
    }
}
