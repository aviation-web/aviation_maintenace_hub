package com.aeromaintenance.Utils;

import java.io.File;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmail(String email,String username, String password) throws MessagingException{
		String to = "nasreenkhan7980@gmail.com"; 
		String from ="aviationteam28@gmail.com"; 
		String host = "smtp.gmail.com";
				  System.out.println("Password: "+password);
				  // Set up properties for the email session 
				  Properties properties = System.getProperties(); 
				  properties.setProperty("mail.smtp.host", host);
				  properties.put("mail.smtp.port", "587"); // Change to 587 for TLS
				  properties.put("mail.smtp.starttls.enable", "true"); // Enable TLS
				  properties.put("mail.smtp.timeout", "120000"); // Timeout settings
				  properties.put("mail.smtp.connectiontimeout", "120000");
				  properties.setProperty("mail.smtp.auth", "true");
				  
				  // Use App Password instead of regular password 
				  String appPassword ="zhinowkxxugollxa"; // Use the App Password generated from Google
				  
				  // Get session object with username and app password 
				  Session session = Session.getInstance(properties, new Authenticator() {
			            @Override
			            protected PasswordAuthentication getPasswordAuthentication() {
			                return new PasswordAuthentication(from, appPassword);
			            }
				  });
				  
				  try { // Create the email message 
					 MimeMessage message = new MimeMessage(session); 
				  message.setFrom(new InternetAddress(from));
				  message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				  message.setSubject("Login Password");
				  String htmlContent = "<html><body><p>UserName: " + username + "</p><p>Password: " + password + "</p></body></html>";
				  message.setContent(htmlContent, "text/html");
				  //message.setText("UserName: "+username+"\nPassword: "+password);
				  
				  // Send the email 
				  Transport.send(message);
				  System.out.println("Email sent successfully."); 
				  } catch (MessagingException e) { 
					  e.printStackTrace(); }
				 
			    }
		/*
		 * MimeMessage message = mailSender.createMimeMessage(); MimeMessageHelper
		 * helper = new MimeMessageHelper(message, true); String htmlContent =
		 * "<html><body><p>UserName: " + username + "</p><p>Password: " + password +
		 * "</p></body></html>"; helper.setTo("nasreen.shaikh2022@gmail.com");
		 * helper.setSubject("Login Password"); helper.setText(htmlContent, true); //
		 * `true` to indicate it's HTML content
		 * helper.setFrom("aviationteam28@gmail.com"); //
		 * message.setRecipients(Message.RecipientType.TO,
		 * "nasreen.shaikh2022@gmail.com"); //
		 * message.setSubject("Password Reset Request"); // // String htmlContent =
		 * "<html><body><p>UserName: " + username + "</p><p>Password: " + password +
		 * "</p></body></html>"; // // message.setContent(htmlContent, "text/html");
		 * 
		 * mailSender.send(message); System.out.println("Email sent successfully.");
		 */
		/*
		 * SimpleMailMessage message = new SimpleMailMessage(); message.setTo(email);
		 * message.setSubject("Password Reset Request");
		 * message.setText("To reset your password, please click the link below:\\n" +
		 * resetLink);
		 * 
		 * mailsender.send(message);
		 */
	//}
	        public void sendAttachmentEmail(String to, String subject, String body, File attachment) throws MessagingException {
	            MimeMessage message = mailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message, true);  // true = multipart

	            helper.setTo(to);
	            helper.setSubject(subject);
	            helper.setText(body);
	            helper.addAttachment(attachment.getName(), attachment);

	            mailSender.send(message);
	        
	        }
	        
	     // Other email sending methods, if needed, e.g., for user registration confirmation
	        public void sendWelcomeEmail(String to) {
	            SimpleMailMessage message = new SimpleMailMessage();
	            message.setTo(to);
	            message.setSubject("Welcome to Our Service");
	            message.setText("Thank you for registering! We are happy to have you on board.");

	            mailSender.send(message);
	        }

}


