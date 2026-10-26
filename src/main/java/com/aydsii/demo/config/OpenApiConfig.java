package com.verdegreen.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI verdeGreenOpenApi() {
        return new OpenAPI().info(new Info()
                .title("VerdeGreen API")
                .description("AyDS II 2026 - Grupo 10 - CU-01 Comprar y CU-02 Consultar seguimiento de compra")
                .version("1.0"));
    }
}
