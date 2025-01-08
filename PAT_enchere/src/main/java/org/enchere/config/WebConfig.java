package org.enchere.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Pour les images uploadées
        registry.addResourceHandler("/images/**")
               .addResourceLocations(System.getProperty("user.home") + "\\Documents/");
        
        // Pour les ressources statiques (comme le logo)
        registry.addResourceHandler("/static/**")
               .addResourceLocations("/PAT_enchere/src/main/resources/static/images/");
    }
}