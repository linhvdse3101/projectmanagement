package com.management.project.wagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

//    @Value("${syn.swagger.dev-url}")
//    private String devUrl;

//    @Value("${syn.swagger.prod-url}")
//    private String prodUrl;

    @Bean
    public OpenAPI customOpenAPI() {
//        Server serverDev = new Server();
//        serverDev.setUrl(devUrl);
//        serverDev.setDescription("Server URL in Development environment");

//        Server serverProd = new Server();
//        serverProd.setUrl(prodUrl);
//        serverProd.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("linhvdse3101@gmail.com");
        contact.setName("BenVo");
//        contact.setUrl("http://www.localhost:8080");
        License mitLicense = new License().name("MIT License").url("https://opensource.org/licenses/MIT");
        // todo BenVo Update servicer prod name and url when deploy
        return new OpenAPI()
                .info(new Info()
                        .title("BenVO API Title")
                        .version("1.0.0")
                        .contact(contact)
//                        .termsOfService("http://localhost:8080/terms-of-service")
                        .license(mitLicense)
                        .description("API Description"));
//                ).servers(List.of(serverDev, serverProd));
//                ).servers(List.of(serverDev));
    }
}
