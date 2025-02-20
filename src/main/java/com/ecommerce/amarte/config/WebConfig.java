package com.ecommerce.amarte.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")  // Aplica CORS a todas las rutas /api/
                        .allowedOrigins("https://musical-capybara-x59gr5qqpqjwhv99v-3000.app.github.dev") // Permite peticiones desde tu frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                        .allowedHeaders("*")  // Permite todos los headers
                        .allowCredentials(true); // Permite envío de credenciales (cookies, tokens, etc.)
            }
        };
    }
}

