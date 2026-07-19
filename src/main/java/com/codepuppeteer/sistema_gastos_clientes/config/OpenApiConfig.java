package com.codepuppeteer.sistema_gastos_clientes.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        final String jwt = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Gestión Financiera API")
                        .description("API REST para gestión financiera Contador-Cliente")
                        .version("v1"))
                .addSecurityItem(new SecurityRequirement().addList(jwt))
                .components(new Components().addSecuritySchemes(jwt,
                        new SecurityScheme()
                                .name(jwt)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
