package com.aeromaintenance.login;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aeromaintenance.Utils.JwtUtil;
import com.aeromaintenance.storeAcceptance.StoreAcc;
@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:8089")
public class LoginController {

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private LoginRepository userRepository;
    
    @Autowired
	private PasswordEncoder passwordEncoder;
    
  
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Optional<Login> userDetails = userRepository.findByUsername(user.getUsername());
//            if (userDetails.get().isPasswordExpired()) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                        .body("Please change your password.");
//            }
            String roles = userDetails.get().getRole();
            //Optional<Login> user = userRepository.findByUsername(authRequest.getUsername());
			/*
			 * Set<String> roles = userDetails .map(Login::getRoles)
			 * .orElse(Collections.emptySet()) .stream() .map(Role::getRoleName)
			 * .collect(Collectors.toSet());
			 */

            String token = jwtUtil.generateToken(user.getUsername(), roles);
            return ResponseEntity.ok(new LoginResponse(token,userDetails.get().isPasswordExpired(),user.getUsername(),roles)); // Return token wrapped in response
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred. Please try again.");
        }
    }
    

	 
	 @PostMapping("/passwordChange") 
	 public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest, @AuthenticationPrincipal UserDetails userDetails) {
		         // Validate the old password
		         Optional<Login> user = userRepository.findByUsername(userDetails.getUsername());
		         
		         // Assuming you are using BCrypt password encoding
		         if (!passwordEncoder.matches(passwordChangeRequest.getCurrentPassword(), user.get().getPassword())) {
		             return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Old password is incorrect.");
		         }

		         // Update password
		         user.ifPresent(login -> {
		        	    login.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
		        	    login.setPasswordExpired(false);  // Reset the flag after change
		        	    userRepository.save(login);  // Save the updated Login object
		        	});

		         return ResponseEntity.ok("Password changed successfully.");
		     }
		 }
