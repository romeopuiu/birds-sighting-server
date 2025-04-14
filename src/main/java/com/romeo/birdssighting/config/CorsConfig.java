package com.romeo.birdssighting.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * Configuration class for enabling Cross-Origin Resource Sharing (CORS).
 * This configuration allows the application to accept HTTP requests from any origin,
 * and supports all HTTP methods (GET, POST, PUT, DELETE) across all endpoints.
 */
@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}
