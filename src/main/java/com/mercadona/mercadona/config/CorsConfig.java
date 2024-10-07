package com.mercadona.mercadona.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        // Autoriser les méthodes HTTP
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        // Limiter l'origine à http://localhost:4200 pour éviter les problèmes de CORS
                        .allowedOrigins("https://unrivaled-douhua-de2908.netlify.app/")  // Autoriser les requêtes venant de localhost:4200

                        // Autoriser tous les headers nécessaires
                        .allowedHeaders("*")
                        // Autoriser les credentials (cookies, headers Authorization)
                        .allowCredentials(true);
            }
        };
    }
}
