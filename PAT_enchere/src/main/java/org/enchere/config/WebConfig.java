package org.enchere.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Pour les images uploadées des articles
        registry.addResourceHandler("/uploads/**")
               .addResourceLocations("file:" + System.getProperty("user.home") + "/Documents/");
        
        // Pour le logo statique
        registry.addResourceHandler("/static/**")
               .addResourceLocations("classpath:/static/");
    }
}