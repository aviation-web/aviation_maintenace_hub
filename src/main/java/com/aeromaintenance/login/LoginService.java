package com.aeromaintenance.login;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aeromaintenance.Utils.EmailService;
import com.aeromaintenance.storeAcceptance.StoreAcc;


@Service
public class LoginService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;

    @Autowired
    private LoginRepository loginRepository;

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:,.<>?/~";

    //    // Register user
//    public String registerUser(Login user) {
//        // Check if a user with the same username already exists
//        if (loginRepository.findByUsername(user.getUsername()).isPresent()) {
//            return "Username already taken. Please choose a different one.";
//        }
//        
//        // Save the user to the database
//        loginRepository.save(user);
//        return "Login " + user.getUsername() + " registered successfully!";
//    }
//
//    // Login check
//    public String validateLogin(String username, String password) {
//        Optional<Login> user = loginRepository.findByUsername(username);
//        if (user.isPresent() && user.get().getPassword().equals(password)) {
//            return "Login Successful!";
//        } else {
//            return "Login Failed!";
//        }
//    }
    // Login check
    private String username;
    private String password;

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
	public String addUser(Login user) {
		String randomPassword = generateRandomPassword(8);
	    String encodedPassword = passwordEncoder.encode(randomPassword);
	    
	    user.setPassword(encodedPassword);
	    user.setPasswordExpired(true);  // Flag to indicate password needs to be changed
	    loginRepository.save(user);
		try {
			emailService.sendEmail(user.getEmail(), user.getUsername(), randomPassword);
			return "Email sent successfully";
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Failed to send email: " + e.getMessage();
		}
		
		
	}

	public Login UpdateUser(Long id, Login updateLogin){
		return loginRepository.findById(id)
				.map(existingLogin -> {
					existingLogin.setFirstName(updateLogin.getFirstName());
					existingLogin.setMiddleName(updateLogin.getMiddleName());
					existingLogin.setLastName(updateLogin.getLastName());
					existingLogin.setUsername(updateLogin.getUsername());
					existingLogin.setDateOfBirth(updateLogin.getDateOfBirth());
					existingLogin.setMobileNumber(updateLogin.getMobileNumber());
					existingLogin.setEmail(updateLogin.getEmail());
					existingLogin.setAddress(updateLogin.getAddress());
					existingLogin.setCity(updateLogin.getCity());
					existingLogin.setState(updateLogin.getState());
					existingLogin.setCountry(updateLogin.getCountry());
					return loginRepository.save(existingLogin);
				})
				.orElse(null);
	}

	public Login getUserById(Long id) {
		return loginRepository.findById(id).orElse(null);
	}

	public void deleteProductById(Long id) {
		loginRepository.deleteById(id);
	}
	
	public String generateRandomPassword(int length) {

	        SecureRandom secureRandom = new SecureRandom();
	        
	        // Ensure the password contains at least one lowercase letter, one uppercase letter, one digit, and one special character.
	        StringBuilder password = new StringBuilder(length);

	        password.append(getRandomCharacter(LOWERCASE, secureRandom)); // at least one lowercase letter
	        password.append(getRandomCharacter(UPPERCASE, secureRandom)); // at least one uppercase letter
	        password.append(getRandomCharacter(DIGITS, secureRandom)); // at least one digit
	        password.append(getRandomCharacter(SPECIAL_CHARACTERS, secureRandom)); // at least one special character

	        // Fill the rest of the password with random characters from all categories to meet the length requirement.
	        String allCharacters = LOWERCASE + UPPERCASE + DIGITS + SPECIAL_CHARACTERS;
	        for (int i = password.length(); i < length; i++) {
	            password.append(getRandomCharacter(allCharacters, secureRandom));
	        }

	        // Shuffle the characters to randomize their order
	        return shufflePassword(password.toString(), secureRandom);
	    }

	    // Helper method to get a random character from a given string of allowed characters
	    private String getRandomCharacter(String characters, SecureRandom secureRandom) {
	        int index = secureRandom.nextInt(characters.length());
	        return String.valueOf(characters.charAt(index));
	    }

	    // Helper method to shuffle the password characters
	    private String shufflePassword(String password, SecureRandom secureRandom) {
	        char[] passwordArray = password.toCharArray();
	        for (int i = 0; i < passwordArray.length; i++) {
	            int j = secureRandom.nextInt(passwordArray.length);
	            char temp = passwordArray[i];
	            passwordArray[i] = passwordArray[j];
	            passwordArray[j] = temp;
	        }
	        return new String(passwordArray);
    }
		public List<Login> getAllUser() {
			
	        List<Login> users = loginRepository.findAll();
	        return users;
		}


}

