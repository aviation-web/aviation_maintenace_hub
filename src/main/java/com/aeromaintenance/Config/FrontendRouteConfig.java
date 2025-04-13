package com.aeromaintenance.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FrontendRouteConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Redirect all unmatched routes to index.html
    	registry.addViewController("/{spring:^(?!api|static|actuator).*}")
        .setViewName("forward:/index.html");
    	registry.addViewController("/**/{spring:^(?!api|static|actuator).*}")
        .setViewName("forward:/index.html");
    }
}
