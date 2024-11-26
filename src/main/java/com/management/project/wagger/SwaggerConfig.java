package com.management.project.wagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
    final String securitySchemeName = "Bearer Authentication";

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail("linhvdse3101@gmail.com");
        contact.setName("BenVo");
        License mitLicense = new License().name("MIT License").url("https://opensource.org/licenses/MIT");
        // todo BenVo Update servicer prod name and url when deploy
        return new OpenAPI()
                .info(new Info()
                        .title("BenVO API Title")
                        .version("1.0.0")
                        .contact(contact)
                        .license(mitLicense)
                        .description("API Description")
                ).addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));

    }
}