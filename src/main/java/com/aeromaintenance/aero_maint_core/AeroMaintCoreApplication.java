package com.aeromaintenance.aero_maint_core;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EntityScan(basePackages = " com.aeromaintenance")
@EnableJpaRepositories(basePackages = "com.aeromaintenance")
@SpringBootApplication(scanBasePackages = "com.aeromaintenance")
public class AeroMaintCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(AeroMaintCoreApplication.class, args);
		System.out.println("Application Run..");
		
		/*
		 * String to = "nasreen.shaikh2022@gmail.com"; String from =
		 * "aviationteam28@gmail.com"; String host = "smtp.gmail.com";
		 * 
		 * // Set up properties for the email session Properties properties =
		 * System.getProperties(); properties.setProperty("mail.smtp.host", host);
		 * properties.put("mail.smtp.port", "587"); // Change to 587 for TLS
		 * properties.put("mail.smtp.starttls.enable", "true"); // Enable TLS
		 * properties.put("mail.smtp.timeout", "60000"); // Timeout settings
		 * properties.put("mail.smtp.connectiontimeout", "60000");
		 * properties.setProperty("mail.smtp.auth", "true");
		 * 
		 * // Use App Password instead of regular password String appPassword =
		 * "zhinowkxxugollxa"; // Use the App Password generated from Google
		 * 
		 * // Get session object with username and app password Session session =
		 * Session.getInstance(properties, new Authenticator() { protected
		 * PasswordAuthentication getPasswordAuthentication() { return new
		 * PasswordAuthentication(from, appPassword); } });
		 * 
		 * try { // Create the email message MimeMessage message = new
		 * MimeMessage(session); message.setFrom(new InternetAddress(from));
		 * message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		 * message.setSubject("Test Email");
		 * message.setText("This is a test email from Java!");
		 * 
		 * // Send the email Transport.send(message);
		 * System.out.println("Email sent successfully."); } catch (MessagingException
		 * e) { e.printStackTrace(); }
		 */
	    }
	

		      
		/*
		 * BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		 * 
		 * // Simulate a raw password (provided by the user) String rawPassword =
		 * "admin@123";
		 * 
		 * // Encode the raw password and store it in the database String
		 * encodedPassword = passwordEncoder.encode(rawPassword);
		 * System.out.println(encodedPassword); // Simulate a login attempt with the raw
		 * password boolean isPasswordMatching = passwordEncoder.matches(rawPassword,
		 * encodedPassword);
		 * 
		 * System.out.println("Password match: " + isPasswordMatching); // true
		 */	    }
	
