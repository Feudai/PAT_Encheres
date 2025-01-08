package org.enchere.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Pour les images uploadées
        registry.addResourceHandler("/uploads/**")
               .addResourceLocations("file:C:/Users/pperrot12024/Documents/PATsave/");
        
        // Pour les ressources statiques (logo)
        registry.addResourceHandler("/images/**")
               .addResourceLocations("classpath:/static/images/")
               .setCachePeriod(0);  // Désactive le cache pendant le développement
    }
}