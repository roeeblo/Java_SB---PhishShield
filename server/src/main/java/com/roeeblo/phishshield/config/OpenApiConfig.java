package com.roeeblo.phishshield.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI phishShieldOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("PhishShield API")
                .description("AI-powered phishing detection service using Google Gemini. " +
                            "Designed to help users identify phishing attempts in Hebrew.")
                .version("1.0.0")
                .contact(new Contact()
                    .name("PhishShield")
                    .url("https://github.com/roeeblo/PhishShield"))
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")))
            .servers(List.of(
                new Server().url("/").description("Current server")
            ));
    }
}

