package com.management.project.appconfigs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig  implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Chỉ định endpoint cần cấu hình
                .allowedOrigins("http://localhost:4200")  // Cho phép frontend từ localhost:4200
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Các phương thức được phép
                .allowedHeaders("*")  // Các header được phép
                .allowCredentials(true);  // Cho phép gửi cookie nếu cần
    }
}
