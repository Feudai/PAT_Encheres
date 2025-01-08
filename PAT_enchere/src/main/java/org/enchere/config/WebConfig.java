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
               .addResourceLocations("file:C:/Users/pperrot12024/Documents/PATsave/");
        
        // Pour les ressources statiques (comme le logo)
        registry.addResourceHandler("/static/**")
               .addResourceLocations("/PAT_enchere/src/main/resources/static/images/");
    }
}