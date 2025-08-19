package com.aeromaintenance.Config;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Session;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig {
	@Autowired
	Environment environment;
	
	 @Bean
	    public JavaMailSender javaMailSender() {
		 Properties properties = System.getProperties();
		 properties.setProperty("mail.debug", "true");
	        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	        mailSender.setHost(environment.getProperty("spring.mail.host").trim());
	        mailSender.setPort(Integer.parseInt(environment.getProperty("spring.mail.port").trim()));
	        mailSender.setUsername(environment.getProperty("spring.mail.username").trim());
	        mailSender.setPassword(environment.getProperty("spring.mail.password").trim());

	        // Optional SMTP properties (authentication, TLS, etc.)
	        java.util.Properties props = mailSender.getJavaMailProperties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	       // props.put("mail.smtp.ssl.enable", "false");
	        props.put("mail.smtp.timeout", "1000000"); // Timeout settings
	        props.put("mail.smtp.connectiontimeout", "1000000");
	        
	     
			return mailSender;

	 }
	 
	 @Bean
	    public ModelMapper modelMapper() {
	        return new ModelMapper();
	    }
	 @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**")
	                        .allowedOrigins(
	                        	"http://localhost:5173",
	                        	"http://13.234.95.189:5173",
	                            "http://13.234.95.189",
	                            "http://13.234.95.189:8082",
	                            "http://13.234.95.189:8080",
	                            "http://13.234.95.189:3000",  // React dev
	                            "http://localhost:3000",     // Local dev
	                            "http://localhost:8089"     // Other dev
	                        )
	                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	                        .allowedHeaders("*")
	                        //.exposedHeaders("Authorization")
	                        .allowCredentials(true);
	            }
	        };
	 }
}
