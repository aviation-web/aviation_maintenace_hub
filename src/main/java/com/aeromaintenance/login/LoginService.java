package com.aeromaintenance.login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

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


}

