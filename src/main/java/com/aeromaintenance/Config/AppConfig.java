package com.aeromaintenance.Config;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

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
}
