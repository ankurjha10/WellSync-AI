package com.wellsync.ai.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI wellSyncOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("WellSync-AI REST API")
                        .description("API for managing oil well operations, CSS cycles, SRP systems, sensor configurations, and production records.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("WellSync-AI Team")));
    }
}
