package com.aeromaintenance.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class LoginController {

//    @Autowired
//    private LoginService loginService;
//    public LoginController() {
//        System.out.println("LoginController Initialized");
//    }
//    // Register user
//    @PostMapping("/register")
//    public String register(@RequestBody Login user) {
//        loginService.registerUser(user);
//        return "User Registered Successfully!";
//    }
//
//    // Login user
//    @PostMapping("/login")
//    public String login(@RequestParam String username, @RequestParam String password) {
//        return loginService.validateLogin(username, password);
//    }
	@PostMapping("/login")
    public String validateLogin(@RequestBody LoginService request) {
        if ("admin".equals(request.getUsername()) && "admin@123".equals(request.getPassword())) {
            return "Login Successful!";
        } else {
            return "Login Failed!";
        }
    }
}
